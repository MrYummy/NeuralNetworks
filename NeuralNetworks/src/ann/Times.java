//Used to generate simple training data

package ann;

public class Times {

  public static void main(String[] args) {
    for (int i = 1; i < 13; i++) {
      for (int j = 1; j < 13; j++) {
        System.out.print("0.");
        for (int k = 0; k < 3-Integer.toString(i).length(); k++) {
          System.out.print("0");
        }
        System.out.print(i+", 0.");
        for (int k = 0; k < 3-Integer.toString(j).length(); k++) {
          System.out.print("0");
        }
        System.out.print(j+", 0.");
        for (int k = 0; k < 3-Integer.toString(i+j).length(); k++) {
          System.out.print("0");
        }
        System.out.println(i+j+",");
      }
    }
  }

}
