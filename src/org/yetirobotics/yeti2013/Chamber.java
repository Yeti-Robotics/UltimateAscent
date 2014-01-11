/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yetirobotics.yeti2013;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Relay;
/**
 * Feeditron
 * 
 * FFFFFFFF EEEEEEEE EEEEEEEE DDDDDD  IIIIII TTTTTTTT RRRRRRR  OOOOOOOO NN    NN
 * FF       EE       EE       DD   DD   II      TT    RR   RRR OO    OO NNNN  NN
 * FFFFFF   EEEEEE   EEEEEE   DD    DD  II      TT    RRRRRRR  OO    OO NN NNNNN
 * FF       EE       EE       DD   DD   II      TT    RR   RRR OO    OO NN   NNN
 * FF       EEEEEEEE EEEEEEEE DDDDDD  IIIIII    TT    RR    RR OOOOOOOO NN    NN
 * 
 * @author pureFloat
 */
public class Chamber {
    boolean forValue;
    boolean backValue;
    Joystick leftJoy;
    Relay pusher;
    DigitalInput forwardSwitch;
    DigitalInput backSwitch;
    
    public Chamber(int pusherPos, int forwardLimitPos, int backLimitPos) {
        pusher = new Relay(pusherPos);
        forwardSwitch = new DigitalInput(forwardLimitPos);
        backSwitch = new DigitalInput(backLimitPos);
        
        }
    public void pusherForwardWithLimits(){
            if (forwardSwitch.get() == true){
                pusher.set(Relay.Value.kReverse);
            }
            else if (forwardSwitch.get() == false) {
                   
                pusher.set(Relay.Value.kOff);       
            }
        //pusher.set(Relay.Value.kReverse);
        }
    public void pusherForwardNoLimits(){
/*
            if (forwardSwitch.get() == true){
                pusher.set(Relay.Value.kReverse);
            }
            else if (forwardSwitch.get() == false) {
                   
                pusher.set(Relay.Value.kOff);       
            }*/
        pusher.set(Relay.Value.kReverse);
        }
        
       /*if(forwardSwitch.get()== false)
       {*/
         
          // pusher.set(Relay.Value.kReverse);
         
         
       //}
       /*else
       {
         pusher.set(Relay.Value.kOff);
         System.out.println("closed");
       }*/
    
public void pusherRetractWithLimits(){
            if (backSwitch.get() == true){
               
                pusher.set(Relay.Value.kForward);
            }
            else if (backSwitch.get() == false) {
                   
                pusher.set(Relay.Value.kOff);       
            }
        /*green-back
        * red-forward(shoot)*/
        //pusher.set(Relay.Value.kForward);
        }

    public void pusherRetractNoLimits(){
            /*if (backSwitch.get() == true){
               
                pusher.set(Relay.Value.kForward);
            }
            else if (backSwitch.get() == false) {
                   
                pusher.set(Relay.Value.kOff);       
            }*/
        /*green-back
        * red-forward(shoot)*/
        pusher.set(Relay.Value.kForward);
        }
        
       /*if(backSwitch.get()== false)
       {*/
         
        // pusher.set(Relay.Value.kForward);
        // System.out.println("open");
         
       //}
       /*else
       {
         pusher.set(Relay.Value.kOff);
         System.out.println("closed");
       }*/
       
    
    public void pusherStop(){
        pusher.set(Relay.Value.kOff);
        System.out.println("closed");
    }
}
    

    
    
