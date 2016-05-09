# coding: utf-8

from bs4 import BeautifulSoup
from flask import Flask, jsonify
app = Flask(__name__)

import urllib, re, json, os

class JDGrabber(object):
    url = r'http://search.jd.com/Search?keyword=笔记本电脑&enc=utf-8&wq=笔记本电脑&pvid=zwlamomi.33xcia'
    dump = r'jd.html'

    def __init__(self):
        pass

    def _parse_brand_model(self, str_brand_model):
        brand = None
        model = None

        if u'(' in str_brand_model:
            mo = re.search(u'^(.*)\(.*\)(.*)$', str_brand_model, re.U)
            if mo:
                brand, model = mo.groups()

        elif u'（' in str_brand_model:
            mo = re.search(u'^(.*)（.*）(.*)$', str_brand_model, re.U)
            if mo:
                brand, model = mo.groups()

        elif str_brand_model.startswith('Apple'):
            brand, model = str_brand_model.split()[:2]

        else:
            # print 'brand & model not found: ', str_brand_model
            pass

        return brand, model

    def _parse_product(self, tag_product):
        # print tag_product

        brand = None
        model = None

        # Parse Price.
        prices = tag_product.select('strong[data-price*="."] i')
        price = prices[0].text if prices else None

        # Parse name and models.
        tag_name_models = tag_product.select('.p-name a')

        if tag_name_models:
            tag_name_model = tag_name_models[0].get('title')

            mo = re.search(u'^(.*)[^0-9\.][\d\.]+英寸', tag_name_model, re.U)
            if mo:
                brand, model = self._parse_brand_model(mo.group(1))
            else:
                # print 'brand_model not found'
                pass

        return price, brand, model

    def _dump_html(self, content):
        ro = open(self.dump, 'w')
        ro.write(content)
        ro.close()

    def _load_html(self):
        ro = open(self.dump, 'r')
        html = ro.read()
        ro.close()

        return html

    def get_products(self, use_dump=False):
        all_products = []

        if use_dump and os.path.exists(self.dump):
            r = self._load_html()
        else:
            r = urllib.urlopen(self.url).read()
            self._dump_html(r)

        soup = BeautifulSoup(r, 'html5lib')
        tag_all_products = soup.select('.gl-warp')[0]

        for tag_each_product in tag_all_products.find_all('li', class_="gl-item"):
            price, brand, model = self._parse_product(tag_each_product)

            if price and brand and model:
                all_products.append(brand+':'+model+':'+price)

        return all_products

    def get_products_dictionaries(self, use_dump=False):
        dictionaries = []

        for each in self.get_products(use_dump):
            brand, model, price = each.split(':')
            price = round(float(price), 3)
            dictionaries.append({"brand":brand, "model":model, "price":price})

        return dictionaries

    def get_products_json(self, use_dump=False):
        json_ = {"provider":"Jingdong"}

        products = []
        for each in self.get_products(use_dump):
            brand, model, price = each.split(':')
            products.append({"brand":brand, "model":model, "price":price})

        json_["products"] = products

        json_ = json.dumps(json_)

        return json_


jdgrabber = JDGrabber()

@app.route('/products')
def products():
    message = jdgrabber.get_products_json(use_dump=True)
    return jsonify(provider='京东',
                   products=jdgrabber.get_products_dictionaries(use_dump=True))




if __name__ == '__main__':
    # jdgrabber = JDGrabber()
    # all_products = jdgrabber.get_products()
    #
    # for each in all_products:
    #     print each
    #
    # products_json = jdgrabber.get_products_json()
    # print products_json

    app.run(host='localhost', port=6001, debug=True)
