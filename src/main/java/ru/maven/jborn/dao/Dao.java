package ru.maven.jborn.dao;

import java.util.List;

public interface Dao<DOMAIN, ID> {

    DOMAIN findById(ID id);

    List<DOMAIN> findByAll();

    DOMAIN insert(DOMAIN domain);

    DOMAIN update(DOMAIN domain);

    boolean delete(ID id);
}