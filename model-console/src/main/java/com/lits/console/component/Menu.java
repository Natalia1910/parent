package com.lits.console.component;

import com.lits.api.dto.ProfileDTO;
import com.lits.api.service.ProfileService;

import java.util.Scanner;

public class Menu {
    private ProfileService profileService;

    public Menu(ProfileService profileService) {
        this.profileService = profileService;
    }

    public void show() {
        System.out.println("1. Find all");
        System.out.println("2. Find by id");
        System.out.println("3. Create");
        System.out.println("4. Update");
        System.out.println("5. Delete");
    }


    public void run(int command) {
        Scanner input = new Scanner(System.in);
        switch (command) {
            case 1:
                profileService.findAll().forEach(e -> {
                    System.out.println("ID = " + e.getId());
                    System.out.println("Name = " + e.getName());
                    System.out.println("LastName = " + e.getLastName());
                    System.out.println("Age = " + e.getAge());
                    System.out.println("-------------------------");
                });
                break;
            case 2:
                System.out.println("Enter id: ");
                try {
                    int id = Integer.parseInt(input.nextLine());
                    findProfileAndPrint(id);
                } catch (NumberFormatException e) {
                    System.out.println("that's not a number, start over");
                }
                break;
            case 3:
                try {
                    System.out.println("Enter new profile's name:");
                    String name = input.nextLine();
                    System.out.println("Enter new profile's last name: ");
                    String lName = input.nextLine();
                    System.out.println("Enter new profile's age: ");
                    int age = Integer.parseInt(input.nextLine());
                    ProfileDTO newProfile = new ProfileDTO();
                    newProfile.setName(name);
                    newProfile.setLastName(lName);
                    newProfile.setAge(age);
                    ProfileDTO savedProfile = profileService.createProfile(newProfile);
                    System.out.println("created new profile with id " + savedProfile.getId());
                } catch (NumberFormatException e) {
                    System.out.println("that's not a number, start over");
                }
                break;
            case 4:
                System.out.println("Enter id: ");
                try {
                    int id = Integer.parseInt(new Scanner(System.in).nextLine());
                    ProfileDTO foundProfile = findProfileAndPrint(id);
                    if (foundProfile == null) {
                        break;
                    }
                    System.out.println("Enter new name:");
                    String name = input.nextLine();
                    System.out.println("Enter new last name: ");
                    String lastName = input.nextLine();
                    System.out.println("Enter new age: ");
                    String ageStr = input.nextLine();
                    if (!name.isEmpty()) foundProfile.setName(name);
                    if (!lastName.isEmpty()) foundProfile.setLastName(lastName);
                    if (!ageStr.isEmpty()) foundProfile.setAge(Integer.parseInt(ageStr));
                    profileService.updateProfile(foundProfile);
                    System.out.println("profile updated");
                } catch (NumberFormatException e) {
                    System.out.println("that's not a number, start over");
                }
                break;
            case 5:
                System.out.println("Enter id: ");
                try {
                    int id = Integer.parseInt(new Scanner(System.in).nextLine());
                    ProfileDTO foundProfile = findProfileAndPrint(id);
                    if (foundProfile == null) {
                        break;
                    }
                    profileService.deleteProfile(id);
                    System.out.println("profile deleted");
                } catch (NumberFormatException e) {
                    System.out.println("that's not a number, start over");
                }
                break;
            case 0:
                System.exit(0);
                break;
            default:
                System.out.println("Wrong command");
                show();
        }
    }

    private ProfileDTO findProfileAndPrint(int id) {
        ProfileDTO profile = profileService.findById(id);
        if (profile == null) {
            System.out.println("profile with id " + id + " not found");
            return null;
        }
        System.out.println("ID = " + profile.getId());
        System.out.println("Name = " + profile.getName());
        System.out.println("LastName = " + profile.getLastName());
        System.out.println("Age = " + profile.getAge());
        System.out.println("-------------------------");
        return profile;
    }
}
