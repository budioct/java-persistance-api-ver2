package com.tutorial.entity;

public class SimpleBrand {

    /**
     *  class ini adalah alias untuk entity Brand karena mempunyai field sebagai DTO (data akses object) menampung data sementara yang nantinya di tersukan ke entity
     */

    private String id;

    private String name;

    public SimpleBrand(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
