package com.company.store;


import com.company.store.model.*;
import com.company.store.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final PaymentRepository paymentRepository;

    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           CustomerRepository customerRepository,
                           ProductRepository productRepository,
                           CompanyRepository companyRepository,
                           PaymentRepository paymentRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    @Override
    public void run(String... args) {
        initRolesAndUsers();
        initProductsAndCompanies();
        initPayments();
    }

    private void initRolesAndUsers() {
        Role adminRole = roleRepository.findByType(RoleType.ROLE_ADMINISTRATOR);
        if (adminRole == null) {
            adminRole = new Role();
            adminRole.setType(RoleType.ROLE_ADMINISTRATOR);
            roleRepository.save(adminRole);
        }

        Role viewerRole = roleRepository.findByType(RoleType.ROLE_VIEWER);
        if (viewerRole == null) {
            viewerRole = new Role();
            viewerRole.setType(RoleType.ROLE_VIEWER);
            roleRepository.save(viewerRole);
        }

        Role customerRole = roleRepository.findByType(RoleType.ROLE_CUSTOMER);
        if (customerRole == null) {
            customerRole = new Role();
            customerRole.setType(RoleType.ROLE_CUSTOMER);
            roleRepository.save(customerRole);
        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(List.of(adminRole, customerRole, viewerRole));
            User user = userRepository.save(admin);

            Customer customer = new Customer();
            customer.setUser(user);
            customer.setEmail("admin@gmail.com");
            customer.setPhone("+375298888888");
            customerRepository.save(customer);
        }

        if (userRepository.findByUsername("customer").isEmpty()) {
            User user = new User();
            user.setUsername("customer");
            user.setPassword(passwordEncoder.encode("customer"));
            user.setRoles(List.of(customerRole, viewerRole));
            userRepository.save(user);

            Customer customer = new Customer();
            customer.setUser(user);
            customerRepository.save(customer);
        }

        if (userRepository.findByUsername("viewer").isEmpty()) {
            User user = new User();
            user.setUsername("viewer");
            user.setPassword(passwordEncoder.encode("viewer"));
            user.setRoles(List.of(viewerRole));
            userRepository.save(user);
        }
    }

    private void initProductsAndCompanies() {
        if (companyRepository.findByName("Bombbar").isEmpty()) {
            Company company = new Company();
            company.setName("Bombbar");
            company.setDescription("Крупный российский производитель полезной снековой продукции.");
            company = companyRepository.save(company);

            if (productRepository.findByArticle("000001").isEmpty()) {
                Product product = new Product();
                product.setArticle("000001");
                product.setCompany(company);
                product.setName("Протеиновый батончик");
                product.setDescription("Шоколад-фундук (1 шт.)");
                product.setPrice(BigDecimal.valueOf(5.30));
                product.setQuantity(100);
                productRepository.save(product);
            }

            if (productRepository.findByArticle("000002").isEmpty()) {
                Product product = new Product();
                product.setArticle("000002");
                product.setCompany(company);
                product.setName("Творожный кекс");
                product.setDescription("Протеиновое печенье (6 шт.)");
                product.setPrice(BigDecimal.valueOf(21.70));
                product.setQuantity(30);
                productRepository.save(product);
            }
        }

        if (companyRepository.findByName("ProteinRex").isEmpty()) {
            Company company = new Company();
            company.setName("ProteinRex");
            company.setDescription("Производитель спортивных добавок, и продукции содержащая натуральный состав.");
            company = companyRepository.save(company);

            if (productRepository.findByArticle("000011").isEmpty()) {
                Product product = new Product();
                product.setArticle("000011");
                product.setCompany(company);
                product.setName("Протеиновое печенье Crispy Ассорти");
                product.setDescription("Вкус Малины (12 шт.)");
                product.setPrice(BigDecimal.valueOf(54.50));
                product.setQuantity(50);
                productRepository.save(product);
            }

            if (productRepository.findByArticle("000012").isEmpty()) {
                Product product = new Product();
                product.setArticle("000012");
                product.setCompany(company);
                product.setName("Протеиновые батончики STRONG");
                product.setDescription("Вкус Фисташка (1 шт.)");
                product.setPrice(BigDecimal.valueOf(3.80));
                product.setQuantity(200);
                productRepository.save(product);
            }
        }
    }

    private void initPayments() {
        if (paymentRepository.findByType(PaymentType.CARD).isEmpty()) {
            Payment payment = new Payment();
            payment.setType(PaymentType.CARD);
            payment.setDescription("Оплата банковской картой");
            paymentRepository.save(payment);
        }

        if (paymentRepository.findByType(PaymentType.OPLATI).isEmpty()) {
            Payment payment = new Payment();
            payment.setType(PaymentType.OPLATI);
            payment.setDescription("Оплата через приложение Оплати");
            paymentRepository.save(payment);
        }

        if (paymentRepository.findByType(PaymentType.PAY_PAL).isEmpty()) {
            Payment payment = new Payment();
            payment.setType(PaymentType.PAY_PAL);
            payment.setDescription("Оплата мобильным телефоном");
            paymentRepository.save(payment);
        }
    }

}
