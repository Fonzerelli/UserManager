package com.ivashyn.util.restPojo;

import com.ivashyn.db.entity.Role;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by ickis on 4/14/15.
 */

@XmlRootElement(name="roles")
public class RoleList {

    private List<Role> data;

    public List<Role> getData() {
        return data;
    }

    public void setData(List<Role> data) {
        this.data = data;
    }
}
