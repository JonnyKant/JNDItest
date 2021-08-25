package com.test.sirena;
import javax.naming.*;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;
import java.util.Properties;

public class ExampleJNDI
{
    static String DNS_CONTEXT = "com.sun.jndi.dns.DnsContextFactory"; //Класс фабрики контекста
    static String DNS_URL     = "dns://77.88.8.8"; // yandex

    ExampleJNDI() {
        Properties props = new Properties ();
        props.put (Context.INITIAL_CONTEXT_FACTORY, DNS_CONTEXT);
        props.put (Context.PROVIDER_URL           ,  DNS_URL   );
        try {
            DirContext dirContext = new InitialDirContext(props);
            //Если предоставляется пустое имя list(), это означает перечисление имен в текущем контексте.
            NamingEnumeration<Binding> names = dirContext.listBindings (".");
            while ( names.hasMoreElements ())
                System.out.println (names.nextElement());

            System.out.println("--------------------");
            //Если предоставляется пустое имя getAttributes(), это означает получение атрибутов, связанных с этим контекстом.
            Attributes root = dirContext.getAttributes("", new String[] {"A", "AAAA", "NS", "SRV", "MX"}); //Можно "*", "* *", "IN *"
            System.out.println(root + "\n" + "--------------------");



            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
            DirContext ictx = new InitialDirContext(env);
            String dnsServers = (String) ictx.getEnvironment().get("java.naming.provider.url");
            String dnsServers1 = (String) dirContext.getEnvironment().get("java.naming.provider.url");
            System.out.println("DNS Servers: " + dnsServers);
            System.out.println("DNS Servers: " + dnsServers1 + "\n" + "--------------------");

            DirContext dirContext1 = (DirContext) dirContext.lookup("car");
            Attributes attributes = dirContext1.getAttributes("");
//            dirContext1.list(""); Так нельзя
            System.out.println(attributes);
            Attributes attributes1 = dirContext.getAttributes("", new String[]{"*"});
            NamingEnumeration<? extends Attribute> namingEnumeration = attributes1.getAll();
            while (namingEnumeration.hasMore())
                System.out.println(namingEnumeration.next());

        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new ExampleJNDI();
        System.exit(0);
    }
}