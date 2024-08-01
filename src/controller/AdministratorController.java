package controller;

import model.Administrator;
import model.AdministratorDAO;

public class AdministratorController {
	private AdministratorDAO administratorDAO = new AdministratorDAO();

    public Administrator getAdministrator(String username) {
        return administratorDAO.searchByUserName(username);
    }

    public void addAdministrator(Administrator administrator) {
        administratorDAO.addAdministrator(administrator);
    }
}
