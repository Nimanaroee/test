import java.util.*;
import java.util.regex.*;

Data data = new Data();

public class Main {

    public static void main(String[] args) {
    }
}

public class Data {
    ArrayList <Member> members = new ArrayList<Member>();
}
class RegisterMenu {
    ArrayList <String> usersList = new ArrayList<String>();
    Mainmenu mainmenu = new Mainmenu();

    void run() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            String command = scan.nextLine();
            if(command.matches("show current menu"))
                System.out.println("Register Menu");
            else if(command.matches("register u (?<username>[^\s]+) p [?<password>[^\s]+] n (?<nickname>[a-zA-Z\s]+)")) {
                registerAccount(getCommandMatcher(command, "register u (?<username>[^\s]+) p [?<password>[^\s]+] n (?<nickname>[a-zA-Z\s]+)"));
            }
            else if(command.matches("login u (?<username>[^\s]+) p (?<password>[^\s]+)")) {
                loginAccount(getCommandMatcher(command, "login u (?<username>[^\s]+) p (?<password>[^\s]+)"));
            }
            else if(command.matches("list of users")) {
                showUsers();
            }
            else if(command.matches("exit")) {
                break;
            }
            else {
                System.out.println("invalid command");
            }
        }
    }
    private Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        matcher.find();
        return matcher;
    }
    private void registerAccount(Matcher matcher) {
        if(!checkUsername(matcher.group("username")))
            System.out.println("username's format is invalid!");
        else if(!checkNickname(matcher.group("nickname")))
            System.out.println("nickname's format is invalid!");
        else if(!checkPassword(matcher.group("password")))
            System.out.println("password is weak!");
        else if(checkUsernameexist(matcher.group("username")))
            System.out.println("username already exists!");
        else {
            Member member = new Member(matcher.group("username"), matcher.group("password"), matcher.group("nickname"));
            this.usersList.add(member.username);
            data.members.add(member);
            System.out.println("user successfully created!");
        }
    }
    private void loginAccount(Matcher matcher) {
        if(!checkUsername(matcher.group("username")))
            System.out.println("username's format is invalid!");
        else if(!checkUsernameexist(matcher.group("username")))
            System.out.println("username doesn't exist!");
        else {
            Member member = getMemberByUsername(matcher.group("username"));
            if(!member.password.equals(matcher.group("password")))
                System.out.println("incorrect password!");
            else {
                System.out.println("user successfully logged in!");
                mainmenu.run(member);
            }
        }
    }
    private void showUsers() {
        for(int i=0 ; i<this.usersList.size() ; i++)
            System.out.println(i+" - "+this.usersList.get(i));
    }
}

class Mainmenu {

    Member run(Member mainMember) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            String command = scan.nextLine();
            if(command.matches("start new game (?<seed>[0-9]+) (?<moves>[0-9]+)")) {

            }
            else if(command.matches("enter (?<menu-name>[]) menu")) {
                Matcher matcher = getCommandMatcher(command, "enter (?<menu-name>[]) menu");
                System.out.println("entered "+matcher.group("menu-name")+" menu!");

                if(matcher.group("menu-name").equals("profile"))
                    profilemenu.run(mainMember);
                else
                    shopmenu.run(mainMember);
                // update member
            }
            else if(command.matches("logout")) {
                break;
            }
            else if(command.matches("show scoreboard")) {
                // sort array by score then username
                // output array
            }
            else {
                System.out.println("invalid command");
            }
        }
    }
    private Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        matcher.find();
        return matcher;
    }
}

class GameMenu {

}

class Shopmenu {

    void run(Member mainMember) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            String command = scan.nextLine();
            if(command.matches("show money"))
                System.out.println("wallet : "+mainMember.money);
            else if(command.matches("show inventory")) {
                System.out.println("Lollipop Hammer : "+mainMember.lollipop_hammer);
                System.out.println("Color Bomb : "+mainMember.color_bomb_brush);
                System.out.println("Extra Moves : "+mainMember.extra_moves);
                System.out.println("Free Switches : "+mainMember.free_switch);
                System.out.println("Stripped Brush : "+mainMember.striped_brush);
                System.out.println("Wrapped Brush : "+mainMember.wrapped_brush);
            }
            else if(command.matches("buy (?<booster>[.]+) (?<count>[0-9]+)")) {
                Matcher matcher = getCommandMatcher(command, "buy (?<booster>[.]+) (?<count>[0-9]+)");
                if(!matcher.group("booster").matches("(free_switch|color_bomb_brush|striped_brush|lollipop_hammer|wrapped_brush|extra_moves)"))
                    System.out.println("there is no product with this name");
                else if(Integer.parseInt(matcher.group("count")) < 1)
                    System.out.println("invalid number");

            }
        }
    }
}

class Member {
    String username, password, nickname;
    int highscore=0, money = 100;
    int lollipop_hammer=0, color_bomb_brush=0, extra_moves=0, free_switch=0, striped_brush=0, wrapped_brush=0;

    Member(String usernameString, String passwordString, String nicknameString) {
        this.username = usernameString;
        this.password = passwordString;
        this.nickname = nicknameString;
    }

    void sethighscore(int newscore) {
        if(this.highscore < newscore)
            this.highscore = newscore;
    }
}
