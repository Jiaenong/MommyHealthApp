package com.example.mommyhealthapp.Class;

public class ProblemPE {
    private String problem;
    private String treatment;
    private String date;
    private String personnel;

    public ProblemPE()
    {

    }

    public ProblemPE(String problem, String treatment, String date, String personnel)
    {
        this.problem = problem;
        this.treatment = treatment;
        this.date = date;
        this.personnel = personnel;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPersonnel() {
        return personnel;
    }

    public void setPersonnel(String personnel) {
        this.personnel = personnel;
    }
}
