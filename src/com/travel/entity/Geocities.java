package com.travel.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Geocities {
    private int geoNameId;
    private String asciiName;
    private String countryRegionCodeIso;

    private Integer population;


    @Id
    @Column(name = "GeoNameID")
    public int getGeoNameId() {
        return geoNameId;
    }

    public void setGeoNameId(int geoNameId) {
        this.geoNameId = geoNameId;
    }

    @Basic
    @Column(name = "AsciiName")
    public String getAsciiName() {
        return asciiName;
    }

    public void setAsciiName(String asciiName) {
        this.asciiName = asciiName;
    }

    @Basic
    @Column(name = "Country_RegionCodeISO")
    public String getCountryRegionCodeIso() {
        return countryRegionCodeIso;
    }

    public void setCountryRegionCodeIso(String countryRegionCodeIso) {
        this.countryRegionCodeIso = countryRegionCodeIso;
    }


    @Basic
    @Column(name = "Population")
    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Geocities geocities = (Geocities) o;
        return geoNameId == geocities.geoNameId &&
                Objects.equals(asciiName, geocities.asciiName) &&
                Objects.equals(countryRegionCodeIso, geocities.countryRegionCodeIso) &&
                Objects.equals(population, geocities.population);
    }

    @Override
    public int hashCode() {
        return Objects.hash(geoNameId, asciiName, countryRegionCodeIso, population);
    }
}
