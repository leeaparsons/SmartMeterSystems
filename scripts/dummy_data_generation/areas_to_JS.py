import csv
import random

def extract_postcode_area(postcode):
    return postcode.split()[0]

def read_csv(input_file):
    with open(input_file, 'r') as file:
        reader = csv.reader(file)
        postcodes = [row[0] for row in reader]
    return postcodes

def generate_commissioned_percentage():
    return round(random.uniform(0, 1), 2)

def write_javascript(output_file, postcode_areas):
    with open(output_file, 'w') as file:
        file.write("const postcodeAreasSorted = [\n")
        for postcode_area in postcode_areas:
            commissioned_percentage = generate_commissioned_percentage()
            file.write(f"    {{\n        postcodeArea: \"{postcode_area}\",\n")
            file.write(f"        commissionedPercentage: {commissioned_percentage},\n")
            file.write("    },\n")
        file.write("];\n")

def main():
    input_file = 'postcodes.csv'
    output_file = 'postcodeAreasSorted.js'

    postcodes = read_csv(input_file)
    postcode_areas = list(set(extract_postcode_area(postcode) for postcode in postcodes))

    write_javascript(output_file, postcode_areas)

if __name__ == "__main__":
    main()