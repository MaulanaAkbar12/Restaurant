/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
    
    private User user;
    
    @Before
    public void setUp() {
        user = new User();
    }
    
    @Test
    public void testSetAndGetId() {
        int id = 1;
        user.setId(id);
        assertEquals(id, user.getId());
    }
    
    @Test
    public void testSetAndGetUsername() {
        String username = "testuser";
        user.setUsername(username);
        assertEquals(username, user.getUsername());
    }
    
    @Test
    public void testSetAndGetPassword() {
        String password = "password123";
        user.setPassword(password);
        assertEquals(password, user.getPassword());
    }
}

