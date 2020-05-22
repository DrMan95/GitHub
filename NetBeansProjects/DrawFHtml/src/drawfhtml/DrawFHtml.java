package drawfhtml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DrawFHtml {
    public static void main(String[] args) throws FileNotFoundException{
        File file = new File("PhiF.html");
        PrintWriter out = new PrintWriter(file);
        Scanner in = new Scanner(System.in);
        int k = in.nextInt();
        String html = "<!DOCTYPE html>\n";
        html += "<html>\n";
        html += "<head>\n";
        html += "<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\" />\n";
        html += "</head\n";
        html += "<body>\n";
        html += "<p style=\"font-size: " + (k*10) + "%\" align=\"center\">F</p> \n";
        html += "</body>\n";
        html += "</html>\n";
        out.print(html);
        out.close();
    }
}
