import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

class Main {
  public static void main(String[] args)
  {

    Scanner us = new Scanner (System.in);
    System.out.println("Does anyone need coverage?(Y/N)");
    String inj = us.nextLine().toUpperCase();
    
    while(!inj.equals("Y")&&!inj.equals("N"))
      {
        System.out.println("Wrong input please enter a capital Y or N as a response");
        inj = us.nextLine().toUpperCase();
      }
    if(inj.equals("N"))
    {
      System.out.println("Succesfully stored the data");
    }
        while(inj.equals("Y"))
      {
        ArrayList<String> noClass = new ArrayList<String>();
    ArrayList<String> schedule = new ArrayList<String>();
    ArrayList<String> gotCov = new ArrayList<String>();

   //TEST: See if my plan works and updatedList actually updates data from the noClass 
   try {
      FileWriter myWriter = new FileWriter("updatedList.txt");
      myWriter.write("");
      myWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    
    //Reads the planning.txt file and adds to noClass arrayList
    try 
      {
      File myObj = new File("planning.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        noClass.add(data);
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
//Reads the schedules.txt file and adds to schedule arrayList
    try 
      {
      File myObj = new File("schedules.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        schedule.add(data);
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    //Let the user enter who needs the coverage
    Scanner input = new Scanner(System.in);
    
    System.out.println("Who needs the coverage?");
    
    String user = input.nextLine();

    String found = "No";

    int indexUser = -1;
    
    for(int i =0; i<schedule.size();i++)
      {
        int o = schedule.get(i).indexOf(":");
        String p = "";
        if(o!=-1)
        {
           p = schedule.get(i).substring(0,o); 
        }
        if(user.equals(p))
        {
          found = user;
          indexUser = i;
           //Checks to see if the person needed coverage is in the noClass ArrayList which contains
            // people who can cover for the person needed
            //If the person's name needed in the coverage is present it removes it from noClass ArrayList
          String n = "";
          for(int j =0; j<noClass.size();j++)
              {
                int u = noClass.get(j).indexOf(":");
                String s = noClass.get(j).substring(0,u);
                if(user.equals(s))
                {
                   n =noClass.remove(j);
                  j--;
                }
              }
          noClass.add(0,n);
        }
      }
//If the name user enter is not in schedule it will 
    //asks the user until the entered a valid name
    while(found.equals("No"))
      {
        System.out.println("Oops. Seems like this name is not in the schedule.txt Please enter a valid name: ");
        user = input.nextLine();
         for(int i =0; i<schedule.size();i++)
        {
          int o = schedule.get(i).indexOf(":");
          String p = schedule.get(i).substring(0,o);
          if(user.equals(p))
          {
            found = user;
            indexUser = i;
            //Checks to see if the person needed coverage is in the noClass ArrayList which contains
            // people who can cover for the person needed
            //If the person's name needed in the coverage is present it removes it from noClass ArrayList
            for(int j =0; j<noClass.size();j++)
              {
                int u = noClass.get(j).indexOf(":");
                String s = noClass.get(j).substring(0,u);
                
                if(user.equals(s))
                {
                  noClass.remove(j);
                  j--;
                }
              }
          }
        }
      }

  //Assigns the user period
String userP = "";
    
 int o = schedule.get(indexUser).indexOf(":");
 userP = schedule.get(indexUser).substring(o+1);

    System.out.println(user +"'s periods are: " + userP);

    //Adds the number of period the person requested for
    String po= "";
    for(int i =0; i<userP.length();i++)
      {
        //Adds the number of periods, in a fromat like
        // 12345
         if(!userP.substring(i,i+1).equals(","))
         {
           po += userP.substring(i,i+1);
         }
      }

    System.out.println();

    //Adds the people who are eligible to cover for other 
    //people in the palnning
    String p ="";
    String al = "";
    String data = "";
    for(int j=0;j<noClass.size();j++)
      {
        al ="";
        int colonInd = noClass.get(j).indexOf(":");
        String name = "";
        if(colonInd!=-1)
        {
          name = noClass.get(j).substring(0,colonInd);
        }
        String periods = noClass.get(j).substring(colonInd+1);
        String n= "";
        
        for(int l=0;l<periods.length();l++)
          {
            String aj = periods.substring(l,l+1);
            for(int k=0;k<po.length();k++)
              {
                if(aj.equals(po.substring(k,k+1)))
                {
                  if(n.equals(""))
                  {
                    if(p.indexOf(po.substring(k,k+1))==-1)
                    {
                      System.out.println(name + " is assigned for period " + po.substring(k,k+1));
                      n= name;
                      al= name;
                      p+=po.substring(k,k+1);
                    }                   
                  }
                }
             }
          }
        
        if(al.equals(name))
        {
         gotCov.add(noClass.remove(j));
         j--;
        }
        
      }

    //Adding people who will cover for the person requested
    // to the noClass arrayList
      for(int i =0; i<gotCov.size();i++)
        {
          noClass.add(gotCov.get(i));
        }
//Checks to see if there is any empty String in noClass arrayList if there is it's going to remove it
    for(int i =0;i<noClass.size();i++)
      {
        if(noClass.get(i).equals(""))
        {
          noClass.remove(i);
          i--;
        }
      }

    try {
      FileWriter myWriter = new FileWriter("planning.txt");
      myWriter.write("");
      myWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    
    //Adds data from noClass to updatedList.txt
    for(int i =0; i<noClass.size();i++)
      { 
         try 
           {
            // Open given file in append mode by creating an
            // object of BufferedWriter class
             
            BufferedWriter out = new BufferedWriter(
                new FileWriter("planning.txt", true));
           String y = noClass.get(i);
        
            // Writing on output stream
            out.write(noClass.get(i) + "\n");
            // Closing the connection
            out.close();            
        }
           
        // Catch block to handle the exceptions
        catch (IOException e) {
 
            // Display message when exception occurs
            System.out.println("exception occurred" + e);
        }
      }

        System.out.println("Does anyone need coverage?(Y/N)");
        inj = us.nextLine().toUpperCase();
        
        while(!inj.equals("Y")&&!inj.equals("N"))
        {
          System.out.println("Wrong input please enter a capital Y or N as a response");
          inj = us.nextLine().toUpperCase();
        }
          
      if(inj.equals("N"))
      {
        System.out.println("Succesfully stored the data");
      }
        
    }
    
  }
    
}
