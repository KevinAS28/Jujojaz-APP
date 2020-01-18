from django.shortcuts import render
from django.http import JsonResponse
from Server_Django.library import jujojaz_login
from .models import *
from django.http.response import HttpResponseNotAllowed
from django.core import serializers
from django.http import HttpResponse
from django.forms import ModelForm
# Create your views here.



@jujojaz_login
def get_all_vehicle(request):
    user = User.objects.get(username=request.POST['username'])
    kendaraan = list(Vehicle.objects.filter(user=user))
    kendaraan_json = serializers.serialize('json', kendaraan)
    return HttpResponse(kendaraan_json, content_type='application/json')        
