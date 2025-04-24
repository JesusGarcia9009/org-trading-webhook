package com.trading.webhook.service;

import java.math.BigDecimal;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import org.ta4j.core.*;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.indicators.*;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import org.ta4j.core.rules.OverIndicatorRule;
import org.ta4j.core.rules.UnderIndicatorRule;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;


@Service
public class StrategyInitialServiceImpl implements StrategyInitialService {\n    public List<HistoricalQuote> fetchData(String symbol) throws IOException {\n        Calendar from = Calendar.getInstance();\n        from.add(Calendar.DATE, -30);\n        Calendar to = Calendar.getInstance();\n        return YahooFinance.get(symbol).getHistory(from, to, Interval.DAILY);\n    }\n\n    public void evaluateDailyStrategy(String symbol) throws IOException {\n        List<HistoricalQuote> historicalQuotes = fetchData(symbol);\n        List<Bar> bars = new ArrayList<>();\n\n        for (HistoricalQuote quote : historicalQuotes) {\n            ZonedDateTime date = ZonedDateTime.ofInstant(quote.getDate().toInstant(), ZoneId.systemDefault());\n            Bar bar = new BaseBar(\n                    date, // Use the ZonedDateTime\n                    quote.getOpen(),\n                    quote.getHigh(),\n                    quote.getLow(),\n                    quote.getClose(),\n                    quote.getVolume()\n            );\n            bars.add(bar);\n        }\n\n        BarSeries series = new BaseBarSeriesBuilder().withBars(bars).withName(symbol).build();\n        // Indicators\n        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);\n        VolumeIndicator volume = new VolumeIndicator(series);\n        HighPriceIndicator highPrice = new HighPriceIndicator(series);\n        LowPriceIndicator lowPrice = new LowPriceIndicator(series);\n\n        EMAIndicator shortEma = new EMAIndicator(closePrice, 9);\n        EMAIndicator longEma = new EMAIndicator(closePrice, 21);\n        MACDIndicator macd = new MACDIndicator(closePrice, 12, 26);\n        RSIIndicator rsi = new RSIIndicator(closePrice, 14);\n\n        // Strategies\n        // 1. Breakout de rango anterior\n        Strategy breakoutStrategy = buildBreakoutStrategy(series, highPrice, volume);\n        boolean breakoutBuy = breakoutStrategy.shouldEnter(series.getLastBarIndex());\n\n        System.out.println(\"Breakout Strategy - Buy signal: \" + breakoutBuy);\n\n        // 2. MACD + RSI\n        Strategy macdRsiStrategy = buildMacdRsiStrategy(macd, rsi);\n        boolean macdRsiBuy = macdRsiStrategy.shouldEnter(series.getLastBarIndex());\n\n        System.out.println(\"MACD + RSI Strategy - Buy signal: \" + macdRsiBuy);\n\n        // 3. EMA cruzada\n        Strategy emaStrategy = buildEmaStrategy(shortEma, longEma);\n        boolean emaBuy = emaStrategy.shouldEnter(series.getLastBarIndex());\n\n        System.out.println(\"EMA Strategy - Buy signal: \" + emaBuy);\n\n    }\n\n    private Strategy buildBreakoutStrategy(BarSeries series, HighPriceIndicator highPrice, VolumeIndicator volume) {\n        // Define a rule for previous day's high.\n        Rule breakoutRule = new OverIndicatorRule(highPrice, highPrice.getValue(series.getEndIndex() - 1));\n        // Define a rule to check volume\n        Rule volumeRule = new OverIndicatorRule(volume, volume.getValue(series.getEndIndex() - 1));\n        // Combine both rules\n        Rule breakoutAndVolumeRule = breakoutRule.and(volumeRule);\n\n        return new BaseStrategy(\"Breakout\", breakoutAndVolumeRule);\n    }\n\n    private Strategy buildMacdRsiStrategy(MACDIndicator macd, RSIIndicator rsi) {\n        // Define a rule for MACD crossing up zero.\n        Rule macdRule = new CrossedUpIndicatorRule(macd, new ConstantIndicator<BigDecimal>(BigDecimal.ZERO));\n        // Define a rule for RSI under 30.\n        Rule rsiRule = new UnderIndicatorRule(rsi, 30);\n        // Combine both rules\n        Rule macdRsiRule = macdRule.and(rsiRule);\n        return new BaseStrategy(\"MACD+RSI\", macdRsiRule);\n    }\n\n    private Strategy buildEmaStrategy(EMAIndicator shortEma, EMAIndicator longEma) {\n        // Define a rule for short EMA crossing up long EMA.\n        Rule emaRule = new CrossedUpIndicatorRule(shortEma, longEma);\n        return new BaseStrategy(\"EMA\", emaRule);\n    }\n}\n
    }
}