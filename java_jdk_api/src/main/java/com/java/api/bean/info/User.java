package com.java.api.bean.info;

/**
 * Created by wuxp on 2016/8/22.
 */
public class User {

    private Long id;

    private String name;

    private Integer age;

    private Boolean sex;

    public User() {
        this.id = 1L;
        this.name="张三";
        this.age=12;
        this.sex=Boolean.FALSE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
