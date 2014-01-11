/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yetirobotics.yeti2013;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Feeditron
 * 
 * FFFFFFFF EEEEEEEE EEEEEEEE DDDDDD  IIIIII TTTTTTTT RRRRRRR  OOOOOOOO NN    NN
 * FF       EE       EE       DD   DD   II      TT    RR   RRR OO    OO NNNN  NN
 * FFFFFF   EEEEEE   EEEEEE   DD    DD  II      TT    RRRRRRR  OO    OO NN NNNNN
 * FF       EE       EE       DD   DD   II      TT    RR   RRR OO    OO NN   NNN
 * FF       EEEEEEEE EEEEEEEE DDDDDD  IIIIII    TT    RR    RR OOOOOOOO NN    NN
 *
 * @author pure_float()
 */
public class Magazine {
    DoubleSolenoid stopperPiston;
    //DigitalInput loadedSwitch;
    public Magazine (int stopperPistonForwardPos, int stopperPistonReversePos/*, int loadedSwitch*/)
    {
        stopperPiston = new DoubleSolenoid(stopperPistonForwardPos, stopperPistonReversePos);
    }   
    public void unload() {
        stopperPiston.set(DoubleSolenoid.Value.kForward);    
    }
    public void prepare() {
        stopperPiston.set(DoubleSolenoid.Value.kReverse);    
    }
   /* public boolean isLoaded()
    {
        return loadedSwitch.get();
    }
    */
}
