# Stocks Tracker Mobile App

A comprehensive Android application for viewing and tracking stock market data with real-time updates and portfolio management.
## Demo
[View Demo Video here](https://drive.google.com/file/d/1f6I5dSAqDtUgTlv1ERS2HnbEjCZym1Mr/view?usp=sharing)

## Project Overview

StocksTracker Mobile is an Android application that enables users to search for stocks, view detailed stock information, manage a watchlist of favorite stocks, and simulate buying and selling stocks with virtual money. The application integrates with the Finnhub API and Polygon.io for financial data and uses MongoDB to store user data.

## Features

### Home Screen
- Current date display
- Portfolio management with Net Worth and Cash Balance tracking
- Stock portfolio list with real-time price updates
- Favorites/Watchlist section
- Swipe to delete functionality for favorites
- Drag and drop reordering of stocks in each section

### Search Functionality
- Auto-complete suggestions for stock symbols
- Detailed stock information upon selection

### Detailed Stock Information
- Company details including name, logo, and current price
- Real-time price updates with trending indicators
- Charts: Hourly and Historical price variations
- Portfolio section for tracking owned shares
- Stats section with high, low, open, and previous close prices
- About section with company details and clickable links
- Insights section with social sentiments, recommendation trends, and EPS surprises
- News section with latest articles and sharing capabilities
- Trade functionality for buying and selling stocks

### Trading System
- Initial $25,000 virtual balance
- Buy and sell stocks at current market prices
- Portfolio tracking with profit/loss calculations
- Transaction success notifications

## Technologies Used

### Frontend
- Native Android (Java)
- Material Design components
- WebView for HighCharts integration

### Backend
- Node.js server with Express
- RESTful API architecture
- MongoDB for data persistence

### External APIs and Libraries
- Finnhub API for stock information, news, and financial metrics
- Polygon.io for historical stock data
- Volley for HTTP requests
- Picasso for image loading and caching
- HighCharts for interactive data visualization

## Project Structure

The project follows standard Android project structure:

- **app/src/main/java/com/example/webtechassignment4/** - Contains Java activity and component files
- **app/src/main/res/** - Contains layout, drawable, and other resource files
- **app/src/main/assets/** - Contains HTML/JavaScript files for HighCharts
- **index.js** - Node.js backend server

## Implementation Details

### Key Components

- **MainActivity**: Home screen with portfolio and favorites
- **detailsactivity**: Detailed stock information display
- **WebAppInterface**: JavaScript interface for WebView charts
- **favoritedata & portfoliodata**: Data adapters for RecyclerViews

### Features Implementation

- **Real-time Updates**: Stock prices are refreshed every 15 seconds
- **Drag & Drop**: ItemTouchHelper for reordering stocks
- **Swipe to Delete**: ItemTouchHelper for removing favorites
- **Charts**: WebView with HighCharts for stock visualizations
- **Trading**: Custom dialogs for buying and selling stocks
- **News Sharing**: Integration with browser for sharing on social media

## Installation and Setup

1. Clone the repository
2. Open the project in Android Studio
3. Make sure you have a Node.js backend running with MongoDB connection
4. Build and run the application on an emulator or physical device (Pixel 7 Pro with API 34 recommended)

## Credits

- Finnhub.io for financial data API
- Polygon.io for historical stock data
- HighCharts for interactive data visualization

## Requirements

- Android Studio
- Android SDK (min SDK 34)
- Node.js and npm
- MongoDB Atlas account
