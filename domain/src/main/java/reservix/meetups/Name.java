package reservix.meetups;

class Name {

    private String name;

    Name(final String name) {
        if(name.length() > 20) {
            throw new IllegalArgumentException("");
        }

        this.name = name;
    }

    String getName() {
        return name;
    }
}
