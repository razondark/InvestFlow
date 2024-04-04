package com.yaroslavyankov.frontend.view.home;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.yaroslavyankov.frontend.dto.Operation;

import java.util.List;

public class OperationWidget extends InfoWidget {

    public OperationWidget(String title, List<Operation> operations) {
        super(title);
        addClassName("operation-widget");

        operations = operations.subList(operations.size() - 10, operations.size() - 4);

        Grid<Operation> grid = new Grid<>(Operation.class, false);
        grid.addColumn(createInputRenderer()).setHeader("Операция")
                .setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(Operation::getPayment).setHeader("Деньга")
                .setAutoWidth(true);
        grid.addColumn(o -> o.getOperationDate().toLocalDate()).setHeader("Дата")
                .setAutoWidth(true)
                .setSortable(true);

        grid.setItems(operations);
        add(grid);

    }

    private static Renderer<Operation> createInputRenderer() {
        return LitRenderer.<Operation>of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "<vaadin-avatar alt=\"User avatar\" style=\"width:40px;\" name=\"${item.ticker}\"><img style=\"max-width:100%;\" src=\"https://img.freepik.com/free-vector/cloud-pos-abstract-concept-illustration_335657-3903.jpg?w=1380&t=st=1712063890~exp=1712064490~hmac=4a02a3921caf1be61075e654a04a77e970e8e9d39cc1e9520850c81922dc2985\"></vaadin-avatar>"
                                + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                                + "    <span> ${item.ticker} </span>"
                                + "    <span style=\"font-size: var(--lumo-font-size-s); color: var(--lumo-secondary-text-color);\">"
                                + "      ${item.name}" + "    </span>"
                                + "  </vaadin-vertical-layout>"
                                + "</vaadin-horizontal-layout>")
                .withProperty("ticker", Operation::getAssetName)
                .withProperty("name", Operation::getFigi);
    }
}
