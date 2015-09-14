/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.as.spv;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author andr
 */
public class CheckerPolandVisaTest {
    
    public CheckerPolandVisaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of run method, of class CheckerPolandVisa.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        CheckerPolandVisa instance = new CheckerPolandVisa();
        instance.run();
        
    }

    
}
