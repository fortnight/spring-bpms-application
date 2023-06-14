package com.company.service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.model.PatientApplication;
import com.company.model.Model;
import com.company.model.Patient;

//import org.jbpm.runtime.manager.api.qualifiers.Task;
import org.jbpm.services.api.ProcessService;
import org.jbpm.services.api.UserTaskService;
import org.jbpm.casemgmt.api.CaseService;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.Status;
//import org.kie.server.services.taskassigning.core.model.Task;
import org.kie.api.task.model.Task;
import org.jbpm.services.api.RuntimeDataService;
 
@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    ProcessService processService;

    @Autowired
    UserTaskService userTaskService;

    @Autowired
    RuntimeDataService runtimeDataService;

    private Map<String, Object> params = new HashMap<String, Object>();

    private PatientApplication patientApp = new PatientApplication(55L, "John", "Hurlocker", "JRH");

    private Patient p = new Patient("John", "Hurlocker", "JRH");

    private Model model = new Model("J", "Hurlocker", "JH");

    @GetMapping("/hello")
	public String hello() {
		return "Hello World RESTful with Spring Boot";
	}

    private String appUser;

    public String getAppUser() {
        return appUser;
    }

    public void setAppUser(String appUser) {
        this.appUser = appUser;
    }

    private String appPass;

    public String getAppPass() {
        return appPass;
    }

    public void setAppPass(String appPass) {
        this.appPass = appPass;
    }
    
    private String appUrl;

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }


    @GetMapping("/startSimpleProcess")
    public String startSimpleProcess(){
	   


	return "Nothing yet";
    }

    @GetMapping("/addpatient")
    public String addPatient(){

        System.out.println("DB URL: " + this.getAppUrl());
        System.out.println("DB USER: " + this.getAppUser());
        System.out.println("DB PASS: " + this.getAppPass());
        //Map<String, Object> properties = new HashMap<>();
        //properties.put("javax.persistence.jdbc.url", this.getAppUrl());
        //properties.put("javax.persistence.jdbc.user", this.getAppUser());
        //properties.put("javax.persistence.jdbc.password", this.getAppPass());
       
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("moh-jpa", properties);
        
		//EntityManager entityManager = emf.createEntityManager();
		//entityManager.getTransaction().begin();

        System.out.println("--- ABOUT TO ADD PATIENT --- ");
        Patient p = new Patient("John", "Hurlocker", "JRH");

        //entityManager.persist(p);
		//entityManager.getTransaction().commit();
        
        System.out.println("--- PATIENT --- " + p.toString());
        return "--- PATIENT --- " + p.toString();
    }
    
    //STARTS THE moh_process
    @GetMapping("/startProcess")
    public String startProcess(){

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("patientApp", this.patientApp);
        params.put("model", this.model);
        long pid = processService.startProcess("eforms-kjar-container1", "moh_process", params);
        return "MOH process started. PID:\n\t{}" + pid;

    }
    
    //STARTS THE moh_process
    @GetMapping("/startKSProcess")
    public String startKSProcess(){
        // System.out.println("DB URL: " + this.getAppUrl());
        // System.out.println("DB USER: " + this.getAppUser());
        // System.out.println("DB PASS: " + this.getAppPass());
        // Map<String, Object> properties = new HashMap<>();
        // properties.put("javax.persistence.jdbc.url", this.getAppUrl());
        // properties.put("javax.persistence.jdbc.user", this.getAppUser());
        // properties.put("javax.persistence.jdbc.password", this.getAppPass());
        
        // EntityManagerFactory emf = Persistence.createEntityManagerFactory("moh-jpa", properties);

        // //EntityManagerFactory emf = Persistence.createEntityManagerFactory("moh-jpa");
        // EntityManager entityManager = emf.createEntityManager();
        // entityManager.getTransaction().begin();

        // System.out.println("--- ABOUT TO ADD PATIENT --- ");

        // patientApp.setFirstName("John");
        // patientApp.setLastName("H");
        
        
        // entityManager.persist(patientApp);
        // // entityManager.flush();
        // System.out.println("---- PATIENT APP ID AFTER FLUSH --- " + patientApp.getId());
        
        // this.params.put("patientApp", patientApp);
        // this.params.put("model", model);

        long pid = processService.startProcess("eforms-kjar-container1", "mohks_process");
        //entityManager.persist(patientApp);
        //entityManager.getTransaction().commit();
        //emf.close();
        return "MOH process started. PID:\n\t{}" + pid;

    }

    //STARTS A TASKS GIVEN A PROCESS INSTANCE ID (pid)
    @PutMapping("/startTask/{pid}")
    public String startTask(@PathVariable(value = "pid") Long pid){

        List<Long> taskList = runtimeDataService.getTasksByProcessInstanceId(pid);
        Long taskId = taskList.get(0);
        
         
        //Task task = userService.getTask(taskId);
        Status taskStatus = userTaskService.getTask(taskId).getTaskData().getStatus();
        System.out.println("---- TASK STATUS ----" + taskStatus.name());
        System.out.println("---- TASK ID ----" + userTaskService.getTask(taskId).getId());
        //Task task = taskService.getTaskById(taskId).getTaskData().getActivationTime();
        if (taskStatus.name().equals(Status.Ready.name())) {
            userTaskService.claim(taskId.longValue(), "kieserver");
            userTaskService.start(taskId.longValue(), "kieserver");
        } 
        else if (taskStatus.name().equals(Status.Reserved.name())) {
            userTaskService.start(taskId.longValue(), "kieserver");
        }

        return "-- STARTED TASK ID --- " + taskId;
    }

    //COMPLETES A TASK GIVEN A PROCESS INSTANCE ID (pid)
    @PutMapping("/completeTask/{pid}")
    public String completeTask(@PathVariable(value = "pid") Long pid){

        List<Long> taskList = runtimeDataService.getTasksByProcessInstanceId(pid);
        Long taskId = taskList.get(0);
        
        Status taskStatus = userTaskService.getTask(taskId).getTaskData().getStatus();
        System.out.println("---- TASK STATUS ----" + taskStatus.name());
        System.out.println("---- TASK ID ----" + userTaskService.getTask(taskId).getId());

		//EntityManager entityManager = this.getEntityManager();
		//entityManager.getTransaction().begin();
        String jpql = "SELECT p FROM PatientApplication p WHERE pid = " + pid + "";
        //TypedQuery<PatientApplication> query = entityManager.createQuery(jpql, PatientApplication.class);

        PatientApplication patientAppQuery = new PatientApplication(); 
        //entityManager.close();
        this.patientApp.setFirstName(patientAppQuery.getFirstName());
        this.patientApp.setLastName(patientAppQuery.getLastName());
        this.patientApp.setPid(patientAppQuery.getPid());
        System.out.println("-- TASK PATIENT APP FIRST= " + patientApp.getFirstName());
        System.out.println("-- TASK PATIENT APP LAST= " + patientApp.getLastName());
        System.out.println("-- TASK PATIENT PID= " + patientApp.getPid());
        
        Map<String, Object> params = new HashMap<String, Object>();
        
        params.put("patientApp", this.patientApp);
        params.put("model", this.model);

        //userService.complete("eforms-kjar-container1", taskId.longValue(), "kieserver", params);
        
        userTaskService.complete(taskId.longValue(), "kieserver", params);
        System.out.println("--** TASK LAST NAME= " + this.patientApp.getLastName());
        //this.updateEntity(patientApp);
        
        return "-- COMPLETED TASK ID --- " + taskId;
    }

    /*public EntityManager getEntityManager(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("moh-jpa");
        EntityManager entityManager = emf.createEntityManager();
        return entityManager;
    }

    public void updateEntity(PatientApplication pa){
        EntityManager entityManager = this.getEntityManager();
		entityManager.getTransaction().begin();
        PatientApplication patientAppUpdate = entityManager.find(PatientApplication.class, pa.getId());
        patientAppUpdate.setLastName(pa.getLastName());
        entityManager.getTransaction().commit();
        entityManager.clear();
    }*/
}
