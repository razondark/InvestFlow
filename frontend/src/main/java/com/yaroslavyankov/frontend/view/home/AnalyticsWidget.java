package com.yaroslavyankov.frontend.view.home;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.charts.model.style.SolidColor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.yaroslavyankov.frontend.dto.Asset;
import com.yaroslavyankov.frontend.dto.PositionResponse;
import com.yaroslavyankov.frontend.util.AssetCounter;
import com.yaroslavyankov.frontend.util.SectorCounter;

import java.util.List;
import java.util.Map;

public class AnalyticsWidget extends InfoWidget {

    public AnalyticsWidget(String title, List<PositionResponse> positions) {
        super(title);
        addClassName("analytic-widget");

        Tab sector = new Tab("Отрасли");
        Tab asset = new Tab("Активы");
        sector.addClassName("analytic-tab");
        asset.addClassName("analytic-tab");
        Tabs tabs = new Tabs(sector, asset);

        Div sectorContainer = new Div();
        sectorContainer.getStyle()
                .set("background-color", "#ffffff");
        Div assetContainer = new Div();
        assetContainer.getStyle()
                .set("background-color", "#ffffff");

        renderSectorChart(positions, sectorContainer);

        tabs.addSelectedChangeListener(event ->

        {
            Tab selectedTab = event.getSelectedTab();

            // Clear any existing charts in both containers
            sectorContainer.removeAll();
            assetContainer.removeAll();

            // Render the appropriate chart based on the selected tab
            if (selectedTab.equals(sector)) {
                renderSectorChart(positions, sectorContainer);
            } else if (selectedTab.equals(asset)) {
                renderAssetChart(positions, assetContainer);
            }
        });

        add(tabs, sectorContainer, assetContainer);

    }


    private void renderSectorChart(List<PositionResponse> positions, Div container) {
        DataSeries series = new DataSeries();
        List<Asset> assets = positions.stream()
                .map(PositionResponse::getAsset).toList();
        Map<String, Long> sectorMap = SectorCounter.countSectors(assets, Asset::getSector);
        int sumOfValues = sectorMap.values().stream()
                .mapToInt(Long::intValue)
                .sum();
        if (sumOfValues == 0) {
            DataSeriesItem item = new DataSeriesItem("Ничего", 100);
            series.add(item);
        } else {
            for (String key : sectorMap.keySet()) {
                DataSeriesItem item = new DataSeriesItem(key, getSectorPercent(sectorMap.get(key), sumOfValues));
                series.add(item);
            }
        }

        Chart chart = configureChart(series);
        container.removeAll();
        container.add(chart);
    }

    private void renderAssetChart(List<PositionResponse> positions, Div container) {
        Map<String, Long> assetMap = AssetCounter.countSectors(positions, PositionResponse::getInstrumentType);
        DataSeries series = new DataSeries();
        int sumOfValues = assetMap.values().stream()
                .mapToInt(Long::intValue)
                .sum();
        if (sumOfValues == 0) {
            DataSeriesItem item = new DataSeriesItem("Ничего", 100);
            series.add(item);
        } else {
            for (String key : assetMap.keySet()) {
                DataSeriesItem item = new DataSeriesItem(key, getSectorPercent(assetMap.get(key), sumOfValues));
                series.add(item);
            }
        }

        Chart chart = configureChart(series);
        container.removeAll();
        container.add(chart);
    }

    private Chart configureChart(DataSeries series) {
        Chart chart = new Chart(ChartType.PIE);

        Configuration conf = chart.getConfiguration();

        Tooltip tooltip = new Tooltip();
        tooltip.setValueDecimals(1);
        conf.setTooltip(tooltip);

        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setAllowPointSelect(true);
        plotOptions.setCursor(Cursor.POINTER);
        plotOptions.setShowInLegend(true);
        conf.setPlotOptions(plotOptions);
        conf.setSeries(series);
        chart.setVisibilityTogglingDisabled(true);

        conf.getChart().setStyledMode(true);
        conf.getLegend().setBackgroundColor(new SolidColor("#F1f1f3"));

        return chart;
    }

    private Number getSectorPercent(Long sectorCount, int sumOfValues) {
        return (double) (sectorCount * 100 / sumOfValues);
    }
}

