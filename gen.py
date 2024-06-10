import pandas as pd
import numpy as np
import uuid
from faker import Faker
import random

# Initialize Faker instance
fake = Faker()

# Number of records
num_locals = 1000
num_items = 5000  

# Define matching European cities and their corresponding countries
city_country_pairs = [
    ("Paris", "France"), ("Berlin", "Germany"), ("Madrid", "Spain"), 
    ("Rome", "Italy"), ("London", "United Kingdom"), ("Amsterdam", "Netherlands"), 
    ("Prague", "Czech Republic"), ("Vienna", "Austria"), ("Budapest", "Hungary"), ("Warsaw", "Poland")
]

# Generate data for Locals
chosen_pairs = [random.choice(city_country_pairs) for _ in range(num_locals)]
chosen_cities = [pair[0] for pair in chosen_pairs]
chosen_countries = [pair[1] for pair in chosen_pairs]

locals_data = {
    'id': [str(uuid.uuid4()) for _ in range(num_locals)],
    'name': [fake.company() for _ in range(num_locals)],
    'description': [fake.catch_phrase() for _ in range(num_locals)],
    'city': chosen_cities,
    'country': chosen_countries,
    'capacity': [fake.random_int(min=10, max=500) for _ in range(num_locals)]
}

locals_df = pd.DataFrame(locals_data)

# Define real food and drink names
foods = ["Pizza", "Burger", "Pasta", "Sushi", "Taco", "Salad", "Steak", "Soup", "Curry", "Sandwich"]
drinks = ["Coke", "Pepsi", "Water", "Coffee", "Tea", "Beer", "Wine", "Juice", "Milkshake", "Smoothie"]

# Generate data for Items
types = [np.random.choice(['FOOD', 'DRINK']) for _ in range(num_items)]  # Choose type for each item
names = [np.random.choice(foods) if t == 'FOOD' else np.random.choice(drinks) for t in types]  # Assign name based on type

items_data = {
    'id': [str(uuid.uuid4()) for _ in range(num_items)],
    'type': types,
    'name': names,
    'description': [fake.sentence() for _ in range(num_items)],
    'price': [round(fake.random_number(digits=2) + fake.random.random(), 2) for _ in range(num_items)],
    'picture': [fake.image_url() for _ in range(num_items)],
    'localId': [np.random.choice(locals_df['id']) for _ in range(num_items)]  # More items per local
}

items_df = pd.DataFrame(items_data)

# Save data to CSV
locals_df.to_csv('Locals.csv', index=False)
items_df.to_csv('Items.csv', index=False)