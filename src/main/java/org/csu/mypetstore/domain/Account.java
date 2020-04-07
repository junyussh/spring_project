package org.csu.mypetstore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "User's information")
public class Account {
    private Integer id;
    @ApiModelProperty(value = "Username",required = true)
    private String username;
    @ApiModelProperty(value = "Password",required = true)
    private String password;
    @ApiModelProperty(value = "Email",required = true)
    private String email;
    @ApiModelProperty(value = "First Name",required = true)
    private String firstName;
    @ApiModelProperty(value = "Last Name",required = true)
    private String lastName;
    private boolean status;
    @ApiModelProperty(value = "Address1",required = true)
    private String address1;
    @ApiModelProperty(value = "Address2",required = true)
    private String address2;
    @ApiModelProperty(value = "City",required = true)
    private String city;
    @ApiModelProperty(value = "State",required = true)
    private String state;
    @ApiModelProperty(value = "Zipcode",required = true)
    private String zip;
    @ApiModelProperty(value = "Country",required = true)
    private String country;
    @ApiModelProperty(value = "Phone number",required = true)
    private String phone;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean getStatus() {
        return status;
    }

    // @JsonProperty means not to serialize this field
    @JsonProperty
    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    @JsonProperty
    public void setId(Integer id) {
        this.id = id;
    }
}
