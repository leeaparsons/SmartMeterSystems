import csv
import json
import random


def generate_json_object(address, postcode, mpxn, device_count):
    json_object = {
        "mpxn": mpxn,
        "address": address,
        "postcode": postcode,
        "devices": [],
        "_class": "com.group15.databaseuploadservice.entity.DataEntry"
    }

    device_types = ["CHF", "ESME", "GPF", "GSME", "PPMID"]
    device_statuses = ["COMMISSIONED", "COMMISSIONED", "COMMISSIONED", "NOT COMMISSIONED"]

    for _ in range(device_count):
        device = {
            "deviceId": "30-EB-5A-FF-FF-08-DD-01",
            "deviceType": random.choice(device_types),
            "deviceStatus": random.choice(device_statuses),
            "deviceManufacturer": "1063",
            "deviceModel": "00050203",
            "deviceFirmwareVersion": "38040404",
            "deviceFirmwareVersionStatus": "NOT ACTIVE",
        }

        json_object["devices"].append(device)

    return json_object


csv_file = 'new_dum_data.csv'
json_array = []

with open(csv_file, 'r') as file:
    reader = csv.DictReader(file)
    mpxn_counter = 1

    for row in reader:
        address = row['address']
        postcode = row['postcode']
        mpxn = str(1023536563501 + mpxn_counter)
        device_count = random.randint(1, 5)
        json_object = generate_json_object(address, postcode, mpxn, device_count)
        json_array.append(json_object)
        mpxn_counter += 1

result_json = {
    "result": json_array
}

json_str = json.dumps(result_json, indent=2)

with open('test_input.json', 'w') as file:
    file.write(json_str)
