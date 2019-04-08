/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Prob15 {
    private static final String INPUT_FILE_NAME = "Prob15.in.txt";
    
    static ArrayList<String> partNumbers = new ArrayList<String>();
    
    // CW3101A( )-( )(P,S)
    static Pattern pattern1 = Pattern.compile("^CW3101A.*-.*[P|S]$");
    // CW426( )-( )
    static Pattern pattern2 = Pattern.compile("^CW426.*-.*$");
    // CW427-( )C( )
    static Pattern pattern3 = Pattern.compile("^CW427-.*C.*$");
    // CW500A4-(3 THRU 8)
    static Pattern pattern4 = Pattern.compile("^CW500A4-[3-8]$");
    // CW507B1032R(8 THRU 32 BY 2)
    static Pattern pattern5 = Pattern.compile("^CW507B1032R(8|10|12|14|16|18|20|22|24|26|28|30|32)$");
    // CW3085-(001 THRU 050,102 THRU 178,201 THRU 284)
    static Pattern pattern6 = Pattern.compile("^CW3085-(00[1-9]|0[1-4][0-9]|050|10[2-9]|1[1-6][0-9]|17[0-8]|20[1-9]|2[1-7][0-9]|28[0-4])$");
    // CWCG20Z-(M,N,P,Q,R,S)(101 THRU 999)B
    static Pattern pattern7 = Pattern.compile("^CWCG20Z-[MNPQRS](10[1-9]|1[1-9][0-9]|[2-9][0-9][0-9])B$");
    // CWDPX2-( )(P,S)( )(P,S)33-00( )
    static Pattern pattern8 = Pattern.compile("^CWDPX2-.*[PS].*[PS]33-00.*$");
    // CWT02(E,P)18-(11,32)(P,S)( )
    static Pattern pattern9 = Pattern.compile("^CWT02[EP]18-(11|32)[PS].*$");
    // CW12326(E,G,J,K,L,M,N,P,R,T)(00375 THRU 20000)(A,S)
    static Pattern pattern10 = Pattern.compile("^CW12326[EGJKLMNPRT](0037[5-9]|003[8-9][0-9]|00[4-9][0-9][0-9]|0[1-9][0-9][0-9][0-9]|1[0-9][0-9][0-9][0-9]|20000)[AS]$");
    // CW15232C(02,04,06,08,3 THRU 6)(-,H)(3 THRU 16,18 THRU 48 BY 2)
    static Pattern pattern11 = Pattern.compile("^CW15232C(02|04|06|08|[3-6])[-H]([3-9]|1[0-6]|18|20|22|24|26|28|30|32|34|36|38|40|42|44|46|48)$");
    // CW15263-(02,04,06,08,3,4,5,6)(-,H)(3 THRU 16,18 THRU 48 BY 2,59)
    static Pattern pattern12 = Pattern.compile("^CW15263-(02|04|06|08|[3-6])[-H]([3-9]|1[0-6]|18|20|22|24|26|28|30|32|34|36|38|40|42|44|46|48|59)$");
    // CW20001(C,P)(H,X,Y)(2,3,4,5,6,8,9,10,12,14,16,17)-(0100 THRU 7200)
    static Pattern pattern13 = Pattern.compile("^CW20001[CP][HXY]([2-6]|[8-9]|10|12|14|1[6-7])-(01[0-9][0-9]|0[2-9][0-9][0-9]|[1-6][0-9][0-9][0-9]|7[01][0-9][0-9]|7200)$");
    // CW102-2-(6 THRU 10,12 THRU 50 BY 2)-(6 THRU 10,12 THRU 50 BY 2)
    static Pattern pattern14 = Pattern.compile("^CW102-2-([6-9]|[1234][02468]|50)-([6-9]|[1234][02468]|50)$");
    // CW8602-( )B( )PNSPM26
    static Pattern pattern15 = Pattern.compile("^CW8602-.*B.*PNSPM26$");
    
    public static void main(String[] args) {
        readInput();
        
        for (int index=0; index<partNumbers.size(); index++) {
            String partNumber = partNumbers.get(index);
            
            Matcher matcher = null;
            
            // CW3101A( )-( )(P,S)
            matcher = pattern1.matcher(partNumber);
            if (matcher.matches()) {
                System.out.println(partNumber);
            } else {
                // CW426( )-( )
                matcher = pattern2.matcher(partNumber);
                if (matcher.matches()) {
                    System.out.println(partNumber);
                } else {
                    // CW427-( )C( )
                    matcher = pattern3.matcher(partNumber);
                    if (matcher.matches()) {
                        System.out.println(partNumber);
                    } else {
                        // CW500A4-(3 THRU 8)
                        matcher = pattern4.matcher(partNumber);
                        if (matcher.matches()) {
                            System.out.println(partNumber);
                        } else {
                            // CW507B1032R(8 THRU 32 BY 2)
                            matcher = pattern5.matcher(partNumber);
                            if (matcher.matches()) {
                                System.out.println(partNumber);
                            } else {
                                // CW3085-(001 THRU 050,102 THRU 178,201 THRU 284)
                                matcher = pattern6.matcher(partNumber);
                                if (matcher.matches()) {
                                    System.out.println(partNumber);
                                } else {
                                    // CWCG20Z-(M,N,P,Q,R,S)(101 THRU 999)B
                                    matcher = pattern7.matcher(partNumber);
                                    if (matcher.matches()) {
                                        System.out.println(partNumber);
                                    } else {
                                        // CWDPX2-( )(P,S)( )(P,S)33-00( )
                                        matcher = pattern8.matcher(partNumber);
                                        if (matcher.matches()) {
                                            System.out.println(partNumber);
                                        } else {
                                            // CWT02(E,P)18-(11,32)(P,S)( )
                                            matcher = pattern9.matcher(partNumber);
                                            if (matcher.matches()) {
                                                System.out.println(partNumber);
                                            } else {
                                                // CW12326(E,G,J,K,L,M,N,P,R,T)(00375 THRU 20000)(A,S)
                                                matcher = pattern10.matcher(partNumber);
                                                if (matcher.matches()) {
                                                    System.out.println(partNumber);
                                                } else {
                                                    // CW15232C(02,04,06,08,3 THRU 6)(-,H)(3 THRU 16,18 THRU 48 BY 2)
                                                    matcher = pattern11.matcher(partNumber);
                                                    if (matcher.matches()) {
                                                        System.out.println(partNumber);
                                                    } else {
                                                        // CW15263-(02,04,06,08,3,4,5,6)(-,H)(3 THRU 16,18 THRU 48 BY 2,59)
                                                        matcher = pattern12.matcher(partNumber);
                                                        if (matcher.matches()) {
                                                            System.out.println(partNumber);
                                                        } else {
                                                            // CW20001(C,P)(H,X,Y)(2,3,4,5,6,8,9,10,12,14,16,17)-(0100 THRU 7200)
                                                            matcher = pattern13.matcher(partNumber);
                                                            if (matcher.matches()) {
                                                                System.out.println(partNumber);
                                                            } else {
                                                                // CW102-2-(6 THRU 10,12 THRU 50 BY 2)-(6 THRU 10,12 THRU 50 BY 2)
                                                                matcher = pattern14.matcher(partNumber);
                                                                if (matcher.matches()) {
                                                                    System.out.println(partNumber);
                                                                } else {
                                                                    // CW8602-( )B( )PNSPM26
                                                                    matcher = pattern15.matcher(partNumber);
                                                                    if (matcher.matches()) {
                                                                        System.out.println(partNumber);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static void readInput() {
        try {
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            
            while ((inLine = br.readLine()) != null) {
                partNumbers.add(inLine);
            }
            
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
