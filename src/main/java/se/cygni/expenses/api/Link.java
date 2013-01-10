package se.cygni.expenses.api;

public class Link {
    private String uri;
    private String operation;
    private String description;
    private String operationId;

    private Link() {
    }

    public Link(String operationId, String uri, String operation, String description) {

        this.operationId = operationId;
        this.uri = uri;
        this.operation = operation;
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public String getOperationId() {
        return operationId;
    }

    public String getOperation() {
        return operation;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Link link = (Link) o;

        if (description != null ? !description.equals(link.description) : link.description != null) {
            return false;
        }
        if (operation != null ? !operation.equals(link.operation) : link.operation != null) {
            return false;
        }
        if (operationId != null ? !operationId.equals(link.operationId) : link.operationId != null) {
            return false;
        }
        if (uri != null ? !uri.equals(link.uri) : link.uri != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = uri != null ? uri.hashCode() : 0;
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (operationId != null ? operationId.hashCode() : 0);
        return result;
    }
}
