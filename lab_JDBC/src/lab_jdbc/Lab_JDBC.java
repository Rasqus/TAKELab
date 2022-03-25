/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_jdbc;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
 

/**
 *
 * @author mkwoj
 */
public class Lab_JDBC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Connection conn = null;
        String connectionString =  
            "jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/" + 
            "dblab02_students.cs.put.poznan.pl";
        Properties connectionProps = new Properties();
        connectionProps.put("user", "inf141274");
        connectionProps.put("password", "inf141274");
        try {
            conn = DriverManager.getConnection(connectionString,connectionProps);
            System.out.println("Połączono z bazą danych");
        } catch (SQLException ex) {
            Logger.getLogger(Lab_JDBC.class.getName()).log(Level.SEVERE, 
                            "Nie udało się połączyć z bazą danych", ex);
            System.exit(-1);
        }
        
        //przyklad
//        try (Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(
//                                    "select id_prac, rpad(nazwisko,14) as nazwisko, placa_pod " +
//                                    "from pracownicy"); ) {
//            while (rs.next()) {
//                System.out.println(rs.getInt("id_prac") + " " + rs.getString("nazwisko") + " " +
//                                    rs.getFloat("placa_pod"));
//            }
//        } catch (SQLException ex) {
//            System.out.println("Błąd wykonania polecenia: " + ex.getMessage());
//        }

        //zadanie 1
        try (Statement stmt1 = conn.createStatement();
             Statement stmt2 = conn.createStatement();
             ResultSet rs1 = stmt1.executeQuery(
                                    "select nazwisko, nazwa " +
                                    "from pracownicy natural inner join zespoly");
             ResultSet rs2 = stmt2.executeQuery(
                                    "select count(*) " +
                                    "from pracownicy natural inner join zespoly"); ) {
            rs2.next();
            System.out.println("Zatrudniono " + rs2.getInt(1) + " pracownikow, w tym:");                        
            while (rs1.next()) {
                System.out.println("  " + rs1.getString(1) + " w zespole " +
                                    rs1.getString(2));
            }
        } catch (SQLException ex) {
            System.out.println("Błąd wykonania polecenia: " + ex.getMessage());
        }

        //zadanie 2
        try (Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                                   ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = stmt.executeQuery(
                                    "select nazwisko, placa_pod " +
                                    "from pracownicy where etat = 'ASYSTENT'" +
                                    "order by placa_pod desc"); ) {
            rs.last();
            System.out.println(rs.getString("nazwisko") + " " +
                               rs.getFloat("placa_pod"));
            rs.relative(-2);
            System.out.println(rs.getString("nazwisko") + " " +
                               rs.getFloat("placa_pod"));
            rs.absolute(2);
            System.out.println(rs.getString("nazwisko") + " " +
                               rs.getFloat("placa_pod"));

        } catch (SQLException ex) {
            System.out.println("Błąd wykonania polecenia: " + ex.getMessage());
        }

        //przyklad
        try (Statement stmt = conn.createStatement();) {

            int changes = stmt.executeUpdate("INSERT INTO " +
                                            "pracownicy(id_prac,nazwisko) " +
                                            "VALUES(5,'Marecki')");
            System.out.println("Wstawiono "+ changes + " krotek.");
            changes = stmt.executeUpdate("UPDATE pracownicy SET "+ 
                                         "placa_pod=placa_pod*1.5");
            System.out.println("Zmodyfikowano "+ changes + " krotek.");
            changes = stmt.executeUpdate("DELETE FROM pracownicy WHERE id_prac=5");
            System.out.println("Usunieto "+ changes + " krotek.");

        } catch (SQLException ex) {
            System.out.println("Błąd wykonania polecenia: " + ex.getMessage());
        }
        
        //zadanie 3
        try (Statement stmt = conn.createStatement();) {

            int [] zwolnienia = {150, 200, 230};
            String [] zatrudnienia = {"KANDEFER", "RYGIEL", "BOCZAR"};

            for (int i = 0; i < zwolnienia.length; i++) {
                stmt.executeUpdate("DELETE FROM pracownicy WHERE id_prac=" + zwolnienia[i]);
            }
            
            stmt.executeUpdate("DROP SEQUENCE prac_seq");
            stmt.executeUpdate("CREATE SEQUENCE prac_seq START WITH 300 increment by 10");
            
            for (int i = 0; i < zatrudnienia.length; i++) {
                stmt.executeUpdate("INSERT INTO pracownicy(id_prac, nazwisko) " +
                                    "VALUES( prac_seq.nextval, '" + zatrudnienia[i] + "' )");
            }


        } catch (SQLException ex) {
            System.out.println("Błąd wykonania polecenia: " + ex.getMessage());
        }

        //zadanie 4
        
        try (Statement stmt = conn.createStatement()) {
            conn.setAutoCommit(false);

            stmt.executeUpdate("DELETE FROM etaty WHERE nazwa = 'EKSPERT'");

            conn.commit();

            try (Statement stmt1 = conn.createStatement();
                 ResultSet rs = stmt1.executeQuery(
                                       "SELECT nazwa FROM etaty "); ) {
                System.out.println("Lista etatow przed dodaniem:");                  
                while (rs.next()) {
                   System.out.println(rs.getString(1));
                }
            } catch (SQLException ex) {
                System.out.println("Błąd wykonania polecenia: " + ex.getMessage());
            }
            
            stmt.executeUpdate("INSERT INTO etaty(nazwa) " +
                               "VALUES('EKSPERT')");
            
            try (Statement stmt1 = conn.createStatement();
                 ResultSet rs = stmt1.executeQuery(
                                       "SELECT nazwa FROM etaty "); ) {
                System.out.println("\nLista etatow po dodaniu eksperta:");                       
                while (rs.next()) {
                   System.out.println(rs.getString(1));
                }
            } catch (SQLException ex) {
                System.out.println("Błąd wykonania polecenia: " + ex.getMessage());
            }                   

            conn.rollback();
            
            try (Statement stmt1 = conn.createStatement();
                 ResultSet rs = stmt1.executeQuery(
                                       "SELECT nazwa FROM etaty "); ) {
                System.out.println("\nLista etatow po wykonaniu rollback:");                        
                while (rs.next()) {
                   System.out.println(rs.getString(1));
                }
            } catch (SQLException ex) {
                System.out.println("Błąd wykonania polecenia: " + ex.getMessage());
            }  

            stmt.executeUpdate("INSERT INTO etaty(nazwa)" +
                               "VALUES('EKSPERT')");

            conn.commit();
            try (Statement stmt1 = conn.createStatement();
                 ResultSet rs = stmt1.executeQuery(
                                       "SELECT nazwa FROM etaty "); ) {
                System.out.println("\nLista etatow po ponownym dodaniu i wykonaniu commit:");                        
                while (rs.next()) {
                   System.out.println(rs.getString(1));
                }
            } catch (SQLException ex) {
                System.out.println("Błąd wykonania polecenia: " + ex.getMessage());
            }       



        } catch (SQLException ex) {
            Logger.getLogger(Lab_JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

        //zadanie 5
        
        try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO pracownicy(id_prac, nazwisko, placa_pod, etat) " +
                                                             "VALUES(prac_seq.nextval, ?, ?, ?) ")) {
            String [] nazwiska = { "WOŹNIAK", "DĄBROWSKI", "KOZŁOWSKI" };
            int [] place =  { 1300, 1700, 1500};
            String [] etaty = { "ASYSTENT", "PROFESOR", "ADIUNKT"};

            for (int i = 0; i < 3; i++)
            {
                pstmt.setString(1, nazwiska[i]);
                pstmt.setInt(2, place[i]);
                pstmt.setString(3, etaty[i]);
                pstmt.executeUpdate();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Lab_JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

        //zadanie 6
        
        try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO pracownicy(id_prac, nazwisko, placa_pod, etat) " +
                                                             "VALUES(prac_seq.nextval, ?, ?, ?) ")) {
            
            long start = System.nanoTime();                                                 
            for (int i = 0; i < 1000; i++)
            {
                pstmt.setString(1, "PRAC_SEKW"+i);
                pstmt.setInt(2, 1000 + i);
                pstmt.setString(3, "STAZYSTA");
                pstmt.executeUpdate();
            }
            long czas1 = System.nanoTime() - start;

            start = System.nanoTime();
            for (int i = 0; i < 1000; i++)
            {
                pstmt.setString(1, "PRAC_WSAD"+i);
                pstmt.setInt(2, 1000 + i);
                pstmt.setString(3, "STAZYSTA");
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            long czas2 = System.nanoTime() - start;

            System.out.println("Czas wykonania sekwencyjnego: " + czas1/1000000000);
            System.out.println("Czas wykonania wsadowego: " + czas2/1000000000);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Lab_JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
		
        //zadanie 7

//        create or replace function ZmianaWielkosciLiter
//            (pid_prac in pracownicy.id_prac%TYPE)
//            return number is 
//            vRet number;
//        begin
//            update pracownicy
//            set nazwisko = substr(nazwisko, 1, 1) || lower(substr(nazwisko, 2))
//            where id_prac = pid_prac;
//            if SQL%NOTFOUND then
//                return 0;
//            end if;
//            return 1;
//        end;

        int [] zamiany = {120, 130, 141};
        int czyUdaloSie = 1;
        
        try (CallableStatement stmt = conn.prepareCall("{? = call ZmianaWielkosciLiter(?)}")) {
            
            for (int i = 0; i < zamiany.length; i++)
            {
                stmt.setInt(2, zamiany[i]);
                stmt.registerOutParameter(1, Types.INTEGER);
                stmt.execute();
                czyUdaloSie = stmt.getInt(1);
                if (czyUdaloSie == 0)
                    System.out.println("Nie udalo sie zmienic wielkosci liter dla pracownika o id: " + zamiany[i]);
                else
                    System.out.println("Udalo sie zmienic wielkosc liter dla pracownika o id: " + zamiany[i]);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Lab_JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }



          


        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Lab_JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Odlaczono od bazy danych");
    }
    
}
