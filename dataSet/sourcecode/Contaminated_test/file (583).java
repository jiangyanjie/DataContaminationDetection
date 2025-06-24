package ATMega1280.Drivers;

import ATMega1280.HardwareObjects.ATMega1280InterruptDispatcher;
import ATMega1280.HardwareObjects.IOReg;
import ATMega1280.HardwareObjects.Port;
import icecaptools.IcecapCompileMe;
import vm.InterruptDispatcher;
import vm.InterruptHandler;

/**
 * Debounced Key driver for EasyAVR M1280 SK board
 * 
 * @author 
 * @date May 8, 2014
 * @filename <code>DebouncedKeys.java</code>
 */
public class DebouncedKeys
{  
   private static IOReg EICRB;
   private static IOReg EIMSK;
   private static Port p;
   
   /** 
    * Initiate Debounced Keys and enables interrupts.
    * @param boolean - init key1
    * @param boolean - init key2
    * @param byte - Trigger mode for interrupt.
    */
   public static void initDebouncedKeys(boolean key1, boolean key2, byte triggerMode)
   {
      EICRB = new IOReg(IOReg.EICRB);
      EIMSK = new IOReg(IOReg.EIMSK);
      p = new Port(Port.PORTE);
      
      if (key1)
         p.DDR &= ~(byte)(1<<Port.PIN4);
      
      if (key2)
         p.DDR &= ~(byte)(1<<Port.PIN5);
      
      setTriggerMode(triggerMode);
      
      if (key1)
      {
         EIMSK.reg |= (1<<IOReg.INT4);
         InterruptDispatcher.registerHandler(new DebouncedKey_1_Handler(), ATMega1280InterruptDispatcher.INT4);
      }
      
      if (key2)
      {
         EIMSK.reg |= (1<<IOReg.INT5);
         InterruptDispatcher.registerHandler(new DebouncedKey_2_Handler(), ATMega1280InterruptDispatcher.INT5);  
      }     
   }

   private static void setTriggerMode(byte triggerMode)
   {
      switch (triggerMode)
      {
         case IOReg.TRIGGER_ALL :
            EICRB.reg |= (1<<IOReg.ISC40) | (1<<IOReg.ISC50);
            EICRB.reg &= ~(1<<IOReg.ISC41) & ~(1<<IOReg.ISC51);
         break;
         
         case IOReg.TRIGGER_RISING_EDGE :
            EICRB.reg |= (1<<IOReg.ISC40) | (1<<IOReg.ISC50) |(1<<IOReg.ISC41) |(1<<IOReg.ISC51);
         break;
         
         case IOReg.TRIGGER_FALLING_EDGE :
            EICRB.reg |= (1<<IOReg.ISC41) | (1<<IOReg.ISC51);
            EICRB.reg &= ~(1<<IOReg.ISC40) & ~(1<<IOReg.ISC50);
         break;
         
         case IOReg.TRIGGER_LOW :
            EICRB.reg &= ~(1<<IOReg.ISC40) & ~(1<<IOReg.ISC50) & ~(1<<IOReg.ISC41) & ~(1<<IOReg.ISC51);
         break;
         
         default : break;
      }
   }
   
   /** 
    * Get output data from pin.
    * @note only the first two bit are returned in a byte
    * @return byte
    */
   public static byte getPinData()
   {
      return  (byte) (p.PIN & 0x3); 
   }
   
   /**
    * Interrupt Handler for Debounced Key 1
    */
   public static class DebouncedKey_1_Handler implements InterruptHandler
   {
      @Override
      @IcecapCompileMe
      public void handle()
      {    
         LED.display((short) 0x55);
      }

      @Override
      public void register()
      {
         // TODO Auto-generated method stub
      }

      @Override
      public void enable()
      {
         // TODO Auto-generated method stub
      }

      @Override
      public void disable()
      {
         // TODO Auto-generated method stub
      }
   }

   /**
    * Interrupt Handler for Debounced Key 2
    */
   public static class DebouncedKey_2_Handler implements InterruptHandler
   {
      @Override
      @IcecapCompileMe
      public void handle()
      {    
         LED.display((short) 0xAA);
      }

      @Override
      public void register()
      {
         // TODO Auto-generated method stub
      }

      @Override
      public void enable()
      {
         // TODO Auto-generated method stub
      }

      @Override
      public void disable()
      {
         // TODO Auto-generated method stub
      }
   }
}


