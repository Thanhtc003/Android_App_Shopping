package com.example.prm392_shopping_project.database;

import java.util.List;

public interface IGenericDB<T> {
    long insert(T t);
    long update(T t);
    long delete(int id);


    T getById(int id);
    List<T> getAll();

    long seedingData();
}
