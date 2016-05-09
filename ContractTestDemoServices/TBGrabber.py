# coding: utf-8

import sys, os
reload(sys)
sys.setdefaultencoding('utf-8')

from bs4 import BeautifulSoup
import urllib, json

from flask import Flask, jsonify
app = Flask(__name__)


class TBGrabber(object):
    url = r'https://s.taobao.com/search?spm=a230r.1.0.0.ca3asv&q=笔记本电脑'
    dump = 'tb.html'

    def __init__(self):
        pass

    def _parse_brand_model(self, brand_model):
        chars = brand_model.split()
        brand = chars[0]
        model = ' '.join(chars[1:])

        return brand, model

    def _parse_product(self, source):
        brand_model_price = []

        _all = json.loads(source)

        for each in _all['mods']['grid']['data']['spus']:

            price = each['price']
            brand, model = self._parse_brand_model(each['title'])

            print 'Brand: {0}, Model: {1}, Price: {2}'.format(brand, model, price)

            brand_model_price.append(brand+':'+model+':'+price)

        return brand_model_price

    def _parse_all_products(self, source):
        anchor = 'g_page_config = '
        brand_model_price = []

        for line in source.splitlines():
            if anchor in line:
                _all = line.split(anchor)[-1].strip()[:-1]
                brand_model_price = self._parse_product(_all)
                break

        return brand_model_price

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

        if use_dump and os.path.exists(self.dump):
            r = self._load_html()
        else:
            r = urllib.urlopen(self.url).read()
            self._dump_html(r)

        soup = BeautifulSoup(r, 'html5lib')
        # print soup

        all_products = self._parse_all_products(soup.text)

        return all_products

    def get_products_dictionaries(self, use_dump=False):
        dictionaries = []

        for each in self.get_products(use_dump):
            brand, model, price = each.split(':')
            price = round(float(price), 3)
            dictionaries.append({"brand":brand, "model":model, "price":price})

        return dictionaries

tbgrabber = TBGrabber()

@app.route('/products')
def products():
    return jsonify(provider='淘宝',
                   products=tbgrabber.get_products_dictionaries(use_dump=True))

if __name__ == '__main__':
    # tbgrabber = TBGrabber()
    # all_products = tbgrabber.get_products()
    #
    # for each in all_products:
    #     print each

    app.run(host='localhost', port=6002, debug=True)
