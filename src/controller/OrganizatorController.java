package controller;

import model.Organizator;
import model.OrganizatorDAO;

public class OrganizatorController {
	private OrganizatorDAO organizatorDAO = new OrganizatorDAO();

    public Organizator getOrganizator(String username) {
        return organizatorDAO.searchByUserName(username);
    }

    public void addOrganizator(Organizator organizator) {
        organizatorDAO.addOrganizator(organizator);
    }
}
