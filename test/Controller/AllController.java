/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Sulthan Fawwaz
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({Controller.ReservationDAOTest.class, Controller.UserDAOTest.class, Controller.DoneDAOTest.class})
public class AllController {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
