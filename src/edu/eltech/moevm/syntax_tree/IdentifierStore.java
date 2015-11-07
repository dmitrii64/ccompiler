package edu.eltech.moevm.syntax_tree;

import sun.reflect.generics.tree.Tree;

import java.util.Iterator;
import java.util.Vector;

/**
 * Created by ivan on 07.11.15.
 */
public class IdentifierStore {
    private Vector<Identifier> identifiers = new Vector<Identifier>();

    private class Identifier {
        public String name;
        public Type type;
        public TreeElement value;
        public boolean used;

        public Identifier(String name, Type type, TreeElement value) {
            this.name = name;
            this.type = type;
            this.used = false;
            this.value = value;
        }

        public boolean hasValue() {
            return value != null;
        }

        public boolean unused() {
            return !used;
        }
    }

    public void createIdentifier(String name, Type type) throws IdentifierDefinedException {
        createIdentifier(name, type, null);
    }

    public void createIdentifier(String name, Type type, TreeElement value) throws IdentifierDefinedException {
        System.out.println("created identifier: "+name);
        if (identifierExists(name)) {
            System.out.println("identifier exists: "+name);

            throw new IdentifierDefinedException();
        }

        identifiers.add(new Identifier(name, type, value));
    }

    public boolean identifierExists(String name) {
        for (Identifier identifier : identifiers) {
            if (identifier.name.compareTo(name) == 0) {
                return true;
            }
        }
        return false;
    }

//    public boolean removeIdentifierByName(String name) {
//        for (Iterator itr = identifiers.listIterator(); itr.hasNext();) {
//            Identifier identifier = (Identifier)itr.next();
//            if (identifier.name == name) {
//                itr.remove();
//                return true;
//            }
//        }
//        return false;
//    }

    public Identifier getFirstUnusedIdentifier() {
        for (Identifier identifier : identifiers) {
            if (identifier.unused()) {
                return identifier;
            }
        }
        return null;
    }
}
