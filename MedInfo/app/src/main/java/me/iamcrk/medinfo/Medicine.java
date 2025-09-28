package me.iamcrk.medinfo;

public class Medicine {
    private String name, searchName, description, uses, sideEffects;

    public Medicine() {} // Required for Firestore

    public String getName() { return name; }
    public String getSearchName() { return searchName; }
    public String getDescription() { return description; }
    public String getUses() { return uses; }
    public String getSideEffects() { return sideEffects; }
}
