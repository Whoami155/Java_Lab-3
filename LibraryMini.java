import java.io.*;
import java.util.*;

class Book implements Comparable<Book> {
    int id; String title, author, cat; boolean issued;
    Book(int i,String t,String a,String c,boolean s){
        id=i; title=t; author=a; cat=c; issued=s;
    }
    public int compareTo(Book b){ return title.compareToIgnoreCase(b.title); }
}

class Member {
    int id; String name; List<Integer> books = new ArrayList<>();
    Member(int i,String n){ id=i; name=n; }
}

public class LibraryMini {
    Map<Integer,Book> books = new HashMap<>();
    Map<Integer,Member> members = new HashMap<>();
    Scanner sc = new Scanner(System.in);

    public static void main(String[] a){
        LibraryMini l = new LibraryMini();
        l.load();
        l.menu();
        l.save();
    }

    void menu(){
        while(true){
            System.out.println("1.Add Book 2.Add Member 3.Issue 4.Return 5.Search 6.Sort 7.Exit");
            int c = Integer.parseInt(sc.nextLine());
            if(c==1) addBook();
            else if(c==2) addMember();
            else if(c==3) issue();
            else if(c==4) ret();
            else if(c==5) search();
            else if(c==6) sort();
            else break;
        }
    }

    void addBook(){
        int id = Integer.parseInt(sc.nextLine());
        String t = sc.nextLine(), a = sc.nextLine(), c = sc.nextLine();
        books.put(id,new Book(id,t,a,c,false));
    }

    void addMember(){
        int id = Integer.parseInt(sc.nextLine());
        String n = sc.nextLine();
        members.put(id,new Member(id,n));
    }

    void issue(){
        int bid=Integer.parseInt(sc.nextLine());
        int mid=Integer.parseInt(sc.nextLine());
        if(books.containsKey(bid)&&members.containsKey(mid)){
            books.get(bid).issued=true;
            members.get(mid).books.add(bid);
        }
    }

    void ret(){
        int bid=Integer.parseInt(sc.nextLine());
        int mid=Integer.parseInt(sc.nextLine());
        if(books.containsKey(bid)&&members.containsKey(mid)){
            books.get(bid).issued=false;
            members.get(mid).books.remove(Integer.valueOf(bid));
        }
    }

    void search(){
        String k=sc.nextLine().toLowerCase();
        for(Book b:books.values())
            if(b.title.toLowerCase().contains(k)||
               b.author.toLowerCase().contains(k)||
               b.cat.toLowerCase().contains(k))
                System.out.println(b.id+" "+b.title);
    }

    void sort(){
        List<Book> list=new ArrayList<>(books.values());
        int c=Integer.parseInt(sc.nextLine());
        if(c==1) Collections.sort(list);
        else if(c==2) list.sort((x,y)->x.author.compareToIgnoreCase(y.author));
        for(Book b:list) System.out.println(b.id+" "+b.title);
    }

    void load(){
        try(BufferedReader br=new BufferedReader(new FileReader("books.txt"))){
            String s;
            while((s=br.readLine())!=null){
                String p[]=s.split(",");
                books.put(Integer.parseInt(p[0]),
                    new Book(Integer.parseInt(p[0]),p[1],p[2],p[3],Boolean.parseBoolean(p[4])));
            }
        }catch(Exception e){}
        try(BufferedReader br=new BufferedReader(new FileReader("members.txt"))){
            String s;
            while((s=br.readLine())!=null){
                String p[]=s.split(",");
                members.put(Integer.parseInt(p[0]),new Member(Integer.parseInt(p[0]),p[1]));
            }
        }catch(Exception e){}
    }

    void save(){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter("books.txt"))){
            for(Book b:books.values()){
                bw.write(b.id+","+b.title+","+b.author+","+b.cat+","+b.issued+"\n");
            }
        }catch(Exception e){}
        try(BufferedWriter bw=new BufferedWriter(new FileWriter("members.txt"))){
            for(Member m:members.values()){
                bw.write(m.id+","+m.name+"\n");
            }
        }catch(Exception e){}
    }
}
