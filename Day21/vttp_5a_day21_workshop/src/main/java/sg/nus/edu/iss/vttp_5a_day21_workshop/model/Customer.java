package sg.nus.edu.iss.vttp_5a_day21_workshop.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Customer {
    private int id;
    private String company;
    private String name;
    private String jobTitle;
    private String businessPhone;
    private String address;
    private String city;
    private String countryRegion;

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryRegion() {
        return countryRegion;
    }

    public void setCountryRegion(String countryRegion) {
        this.countryRegion = countryRegion;
    }

    public static Customer createCustomer(SqlRowSet rowSet){
        Customer c = new Customer();
        c.setId(rowSet.getInt("id"));
        c.setCompany(rowSet.getString("company"));

        // Add the first and last name together
        c.setName(new StringBuilder().append(rowSet.getString("first_name")).append(" ")
        .append(rowSet.getString("last_name")).toString());
        c.setJobTitle(rowSet.getString("job_title"));
        c.setBusinessPhone(rowSet.getString("business_phone"));
        c.setAddress(rowSet.getString("address"));
        c.setCity(rowSet.getString("city"));
        c.setCountryRegion(rowSet.getString("country_region"));

        return c;
    }

    
}
