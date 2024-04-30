# Load necessary library
library(tidyr)
library(ggplot2)
library(dplyr)
library(readxl)

# Read the Excel file
data_frame <- read_excel(file_path)
file_path <- "national_M2023_dl.xlsx"

# Assuming your data is already read into 'data_frame'
# Convert all relevant columns to numeric if not already
data_frame$A_MEDIAN <- as.numeric(as.character(data_frame$A_MEDIAN))
data_frame$H_PCT10 <- as.numeric(as.character(data_frame$H_PCT10))

# Split the data into sets with and without missing A_MEDIAN values
train_data <- data_frame %>% filter(!is.na(A_MEDIAN) & !is.na(H_PCT10))
test_data <- data_frame %>% filter(is.na(A_MEDIAN) & !is.na(H_PCT10))

# Create the linear model
model <- lm(A_MEDIAN ~ H_PCT10, data = train_data)

# Predict the A_MEDIAN for missing values
predicted_values <- predict(model, newdata = test_data)

# Replace NA in original data with predicted values
data_frame$A_MEDIAN[is.na(data_frame$A_MEDIAN)] <- predicted_values

# Check results
summary(data_frame)
View(data_frame)

# Convert A_MEDIAN to numeric. This will turn non-numeric strings to NA
data_frame$A_MEDIAN <- as.numeric(as.character(data_frame$A_MEDIAN))

# Handle possible warnings or errors in conversion
warnings()

# Now sort the data by A_MEDIAN in descending order
sorted_data <- data_frame %>%
  arrange(desc(A_MEDIAN), .na.last = TRUE)

# View the top entries in the sorted data
head(sorted_data)
View(sorted_data)

# Assuming your data is already read into 'data_frame'
# Convert A_MEDIAN and A_MEAN to numeric if not already
data_frame$A_MEDIAN <- as.numeric(as.character(data_frame$A_MEDIAN))
data_frame$A_MEAN <- as.numeric(as.character(data_frame$A_MEAN))

# Create a new data frame for plotting top 10 occupations by A_MEDIAN
top_occupations <- data_frame %>%
  drop_na(A_MEDIAN) %>%
  arrange(desc(A_MEDIAN)) %>%
  top_n(20) %>%
  mutate(OCC_TITLE = factor(OCC_TITLE, levels = OCC_TITLE[order(A_MEDIAN)]))

# Plotting the top 10 occupations by median annual wage
ggplot(top_occupations, aes(x = OCC_TITLE, y = A_MEDIAN, fill = OCC_TITLE)) +
  geom_bar(stat = "identity") +
  theme_minimal() +
  labs(title = "Top 10 Occupations by Median Annual Wage",
       x = "Occupation Title",
       y = "Median Annual Wage (A_MEDIAN)") +
  coord_flip() # Flips the axes for better readability of occupation titles

# Scatter plot to compare A_MEDIAN and A_MEAN across all occupations
ggplot(data_frame, aes(x = A_MEDIAN, y = A_MEAN)) +
  geom_point(aes(color = OCC_TITLE), alpha = 0.5) +
  theme_minimal() +
  theme(plot.margin = unit(c(1, 1, 1, 1), "cm")) +  # Adjust top, right, bottom, and left margins
  labs(title = "Comparison of Median and Mean Annual Wages Across Occupations",
       x = "Median Annual Wage (A_MEDIAN)",
       y = "Mean Annual Wage (A_MEAN)") +
  geom_smooth(method = "lm", color = "blue", se = FALSE)

# Execute the plot commands to display the charts
ggsave("top_occupations_bar_chart.png")
ggsave("median_mean_scatter_plot.png")

