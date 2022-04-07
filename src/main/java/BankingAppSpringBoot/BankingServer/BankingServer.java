package BankingAppSpringBoot.BankingServer;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class BankingServer {

    HashMap<String, BankingAccount> bankUser;

    public BankingServer(){
        this.bankUser = new HashMap<>();
        this.bankUser.put("Bavo",new BankingAccount("Bavo",1000));
    }
    @GetMapping("/BankingServer/users/{user}")
    public String getBalance(@PathVariable(value = "user") String user) {
        long balance;
        if (bankUser.containsKey(user)) {
            balance = bankUser.get(user).getBalance();
            return "User " + user + " has balance: " + balance;
        }
        return "User " + user + " doesn't exist";

    }
    @PutMapping("BankingServer/users/{user}")
    public String putBalance(@PathVariable(value = "user") String user, @RequestBody String money) {
        long balance;
        if (bankUser.containsKey(user)) {
            balance = bankUser.get(user).putBalance(Integer.parseInt(money)) ;
            return "User " + user + " new balance: " + balance;
        }
        return "User " + user + " doesn't exist";
    }
    //We add user1 to user2
    @PostMapping("BankingServer/users/{user1}/{user2}")
    public String jointAccount(@PathVariable(value = "user1") String user1, @PathVariable(value = "user2") String user2){
        long balance;
        if(!bankUser.containsKey(user1)){
            if(bankUser.containsKey(user2)) {
                bankUser.put(user1, bankUser.get(user2));
            }else{
                return "User " + user2 + " Doesn't exist";
            }
        }else{
            return "User " + user1 + " Already exists";
        }
        balance = bankUser.get(user1).getBalance();
        return "User " + user1 + " Added to " + user2 + " with balance: " + balance;
    }

    @PostMapping("BankingServer/users/{user}")
    public String newUser(@PathVariable(value = "user") String user, @RequestBody int money){
        long balance;
        if(!bankUser.containsKey(user)){
            bankUser.put(user, new BankingAccount(user, money));
        }
        balance = bankUser.get(user).getBalance();
        return "User " + user + " Added with balance: " + balance;
    }
    @DeleteMapping("BankingServer/users/{user}")
    public String deleteUser(@PathVariable(value = "user") String user) {
        if (bankUser.containsKey(user)) {
            bankUser.remove(user);
            return "User " + user + " Removed";
        }
        return "User " + user + " was not removed because he doesn't exist";
    }
}
