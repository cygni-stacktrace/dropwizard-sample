package se.cygni.expenses.api;

import java.util.ArrayList;
import java.util.List;

public class OperationResult {

    private List<Link> links = new ArrayList<Link>();

    public List<Link> getLinks() {
        return new ArrayList<Link>(links);
    }

    public void addLink(Link link) {
        links.add(link);
    }
}
