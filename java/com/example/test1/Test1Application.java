package com.example.test1;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class Test1Application {
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = SpringApplication.run(Test1Application.class, args);
		DatabaseRepository repository = context.getBean(DatabaseRepository.class);

		@PageTitle("Pizzeria - Oliwia Paliwoda")
		 class MainView extends VerticalLayout {
			public MainView() {

				Menu menu = new Menu(repository);
				MenuView menuView = new MenuView(menu, repository);
				add(menuView);
			}
		}

	}

}
