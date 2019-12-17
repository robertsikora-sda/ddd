package reservix.meetups;

class Name {

    private String name;

    public Name(final String name) {
        if(name.length() > 20) {
            throw new IllegalArgumentException("");
        }

        this.name = name;
    }
}
