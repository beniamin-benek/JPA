package pl.bl.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("db");
        EntityManager entityManager = factory.createEntityManager();

        Product product1 = new Product("Masło", new BigDecimal("5.59"));
        Product product2 = new Product("Szynka", new BigDecimal("16.99"));

        entityManager.getTransaction().begin();
        entityManager.persist(product1);
        entityManager.persist(product2);

        Query query = entityManager.createQuery("select p from Product p");

        Query query2 = entityManager.createQuery("select p from Product p order by p.price", pl.bl.jpa.Product.class);

        System.out.println("Podaj po czym sortować: 1 - po nazwie rosnąco, 2 - po nazwie malejąco, 3 - po cenie rosnąco, 4 - po cenie malejąco:");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        String option = "";

        switch (i) {
            case 1:
                option = "select p from Product p order by p.name";
                break;
            case 2:
                option = "select p from Product p order by p.name desc";
                break;
            case 3:
                option = "select p from Product p order by p.price";
                break;
            case 4:
                option = "select p from Product p order by p.price desc";
                break;
        }

        //String queryString = "select p from Product p order by" + option;

        Query query3 = entityManager.createQuery(option, pl.bl.jpa.Product.class);

        for (Object result : query3.getResultList()) {
            Product product = (Product) result;
            System.out.println(product.getName() + ", " + product.getPrice());
        }

//        for(Object result : query.getResultList()) {
//            Product product = (Product) result;
//            System.out.println(product.getName());
//        }

        entityManager.getTransaction().commit();

        entityManager.close();
        factory.close();

    }


}