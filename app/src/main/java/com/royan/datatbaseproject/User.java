package com.royan.datatbaseproject;

import org.json.JSONException;
import org.json.JSONObject;

public class User
{
    private String title;
    private String first;
    private String last;
    private String gender;
    private String street;
    private String city;
    private String country;
    private String postcode;

    public User(JSONObject jsonObj) throws JSONException
    {
        JSONObject nameObj = jsonObj.getJSONObject("name");
        JSONObject locationObj = jsonObj.getJSONObject("location");
        JSONObject jsonObjStreet = locationObj.getJSONObject("street");

        this.title = nameObj.getString("title");
        this.first = nameObj.getString("first");
        this.last = nameObj.getString("last");
        this.gender = jsonObj.getString("gender");
        this.city = locationObj.getString("city");
        this.street = jsonObjStreet.getString("name") + " " + jsonObjStreet.getString("number");
        this.country = locationObj.getString("country");
        this.postcode = locationObj.getString("postcode");
    }

    public User(String title, String first, String last, String gender, String street, String city, String country, String postcode)
    {
        this.title = title;
        this.first = first;
        this.last = last;
        this.gender = gender;
        this.street = street;
        this.city = city;
        this.country = country;
        this.postcode = postcode;
    }

    public String getTitle()
    {
        return title;
    }

    public String getFirst()
    {
        return first;
    }

    public String getLast()
    {
        return last;
    }

    public String getGender()
    {
        return gender;
    }

    public String getStreet()
    {
        return street;
    }

    public String getCity()
    {
        return city;
    }

    public String getCountry()
    {
        return country;
    }

    public String getPostcode()
    {
        return postcode;
    }
}
