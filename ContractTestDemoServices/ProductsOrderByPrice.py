import urllib, json
import sys
reload(sys)
sys.setdefaultencoding('utf-8')

from flask import Flask





PRODUCTS_URL_JD = 'http://localhost:5000/products'
PRODUCTS_URL_TB = 'http://localhost:5001/products'

app = Flask(__name__)

def get_products_jd():
    products = urllib.urlopen(PRODUCTS_URL_JD).read()
    products_json = json.loads(products)

    return products_json

def get_products_tb():
    pass

def sort_products_by_price(products):
    items = products

    sorted_products = sorted(items, key=lambda x:x['price'], reverse=False)

    return sorted_products

def _show_products(products):
    for each in products:
        print 'Brand: {0}, Model: {1}, Price: {2}'.format(each['brand'], each['model'], each['price'])



if __name__ == '__main__':
    products_jd_json = get_products_jd()
    provider = products_jd_json['provider']
    products = products_jd_json['products']


    print 'Provider: {0}'.format(provider)
    _show_products(products)

    print
    products_sorted_by_price = sort_products_by_price(products)
    _show_products(products_sorted_by_price)
