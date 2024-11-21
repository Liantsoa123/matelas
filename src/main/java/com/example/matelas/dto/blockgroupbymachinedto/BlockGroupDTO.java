package com.example.matelas.dto.blockgroupbymachinedto;

public class BlockGroupDTO {


    int id;
     String machineName;
     long quantite;
     double totalPrixRevient;
     double totalPrixTheorique;
     double difference;

    public BlockGroupDTO(int id, String machineName, long quantite,
                         double totalPrixRevient, double totalPrixTheorique, double difference) {
        this.id = id;
        this.machineName = machineName;
        this.quantite = quantite;
        this.totalPrixRevient = totalPrixRevient;
        this.totalPrixTheorique = totalPrixTheorique;
        this.difference = difference;
    }




    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public long getQuantite() {
        return quantite;
    }

    public void setQuantite(long quantite) {
        this.quantite = quantite;
    }

    public double getTotalPrixRevient() {
        return totalPrixRevient;
    }

    public void setTotalPrixRevient(double totalPrixRevient) {
        this.totalPrixRevient = totalPrixRevient;
    }

    public double getTotalPrixTheorique() {
        return totalPrixTheorique;
    }

    public void setTotalPrixTheorique(double totalPrixTheorique) {
        this.totalPrixTheorique = totalPrixTheorique;
    }

    public double getDifference() {
        return difference;
    }

    public void setDifference(double difference) {
        this.difference = difference;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
