class Id {
    String id;
    Id(String id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return id;
    }
    static int x = -1;
    static Id gen() {
        x++;
        return new Id("?v" + x);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj==this) {
            return true;
        }
 
        if (obj instanceof Id) {
            Id other = (Id) obj;
 
            if (!this.id.equals(other.id)) {
                return false; 
            }

            return true;
        }
        return false;
    }

}
