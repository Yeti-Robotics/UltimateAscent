/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yetirobotics.yeti2013;
import edu.wpi.first.wpilibj.*;



/**
 * Climbinator
 * 
 * CCCCCC LL    IIIIII MMM  MMM BBBBBBB  IIIIII NN    NN    AAA   TTTTTTTT OOOOOOOO RRRRRRR
 * CC     LL      II   MMM  MMM BB   BBB   II   NNNN  NN   AA AA     TT    OO    OO RR   RRR
 * CC     LL      II   MM MM MM BBBBBBB    II   NN NNNNN  AA   AA    TT    OO    OO RRRRRRR
 * CC     LL      II   MM    MM BB   BBB   II   NN   NNN AAAAAAAAA   TT    OO    OO RR   RRR
 * CCCCCC LLLLLL  II   MM    MM BBBBBBB  IIIIII NN    NN AA     AA   TT    OOOOOOOO RR    RR
 * 
 * @author pureFloat
 */
public class Climber {
   Relay winchSpike; 
   DigitalInput climberSwitch;
   DoubleSolenoid climberPiston;
   
   public Climber(int climberPiston1, int climberPiston2) {
       climberPiston = new DoubleSolenoid(climberPiston1, climberPiston2);
       //winchSpike = new Relay(winchSpikePos);
       //climberSwitch = new DigitalInput(climberSwitchPos);
    }
   public void climbUp(){
       climberPiston.set(DoubleSolenoid.Value.kForward);
       System.out.println("climbing up");
       /*if(climberSwitch.get()!= true)
       {
         winchSpike.set(Relay.Value.kOff);
         System.out.println("closed");
       }
       else
       {
         winchSpike.set(Relay.Value.kReverse);
         System.out.println("open");
       }*/
   }
   public void climbDown(){
       climberPiston.set(DoubleSolenoid.Value.kReverse);
       System.out.println("climbing down");
        //winchSpike.set(Relay.Value.kForward);
   }
   public void stop() {
       climberPiston.set(DoubleSolenoid.Value.kOff);
       //winchSpike.set(Relay.Value.kOff);
   }
   //public boolean getSwitchTop() {
       //return climberSwitch.get();
   //}
}