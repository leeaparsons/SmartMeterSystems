import csv

def extract_postcode_district(postcode):
    return postcode.split()[0]

def read_csv(input_file):
    with open(input_file, 'r') as file:
        reader = csv.reader(file)
        postcodes = [row[0] for row in reader]
    return postcodes

def write_csv(output_file, data):
    with open(output_file, 'w', newline='') as file:
        writer = csv.writer(file)
        for row in data:
            writer.writerow([row])

def main():
    input_file = 'postcodes.csv'
    output_file = 'postcode_areas.csv'

    postcodes = read_csv(input_file)
    unique_postcode_districts = set(extract_postcode_district(postcode) for postcode in postcodes)

    write_csv(output_file, unique_postcode_districts)

if __name__ == "__main__":
    main()