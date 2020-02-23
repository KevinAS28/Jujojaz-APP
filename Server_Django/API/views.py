from django.shortcuts import render
from django.http import JsonResponse
from Server_Django.library import jujojaz_login
from .models import *
from django.http.response import HttpResponseNotAllowed, JsonResponse
from django.core import serializers
from django.http import HttpResponse
from django.forms import ModelForm
import json
import base64
import Server_Django.settings as settings
import os
# Create your views here.


def test(request):
    data = json.loads(request.POST['data'])
    image = data['image']
    with open(os.path.join(settings.BASE_DIR, 'image.jpg'), 'wb') as writer:
        b64_string = image.replace(" ", "+")
        print(b64_string)
        b64_byte = base64.decodebytes(b64_string.encode())
        print(len(b64_byte))
        #b64_string += "=" * ((4 - len(b64_string) % 4) % 4) #ugh
        writer.write(b64_byte)
    return HttpResponse('')

def create_account(request):
    data = json.loads(request.POST['data'])
    username = data['username']
    password = data['password']
    User(username=username, password=password).save()
    return HttpResponse()

@jujojaz_login
def get_all_vehicle(request):
    user = User.objects.get(username=json.loads(request.POST['data'])['username'])
    
    kendaraan = list(Vehicle.objects.filter(user=user))
    kendaraan_json = json.loads(serializers.serialize('json', kendaraan))
    data = {"success": "true", "data": kendaraan_json}        
    return JsonResponse(data)

@jujojaz_login
def edit_vehicle(request):
    id_kendaraan = request.POST["id_kendaraan"]
    kendaraan = Vehicle.objects.get(id=id_kendaraan)
    
    merk_nama = request.POST["merk"]
    merks = list(VehicleMerk.objects.filter(name=merk_nama))
    if (len(merks)):
        merk_object = merks[0]    
    else:
        merk_object = VehicleMerk(name=merk_nama)
        merk_object.save()

    type_nama = request.POST["tipe"]
    types = list(Vehicletype.objects.filter(name=type_nama))
    if (len(types)):
        type_object = types[0]    
    else:
        type_object = VehicleType(name=type_nama)
        type_object.save()
    
    user = User.objects.get(username=request.POST['username'])

    kendaraan.tipe = type_object
    kendaraan.merk = merk_object
    kendaraan.pajak_setiap_berapa_hari = int(request.POST["pajak_setiap_berapa_hari"])
    kendaraan.pajak_dimulai = request.POST["pajak_dimulai"]
    kendaraan.servis_setiap_berapa_hari = int(request.POST["servis_setiap_berapa_hari"])
    kendaraan.servis_dimulai = request.POST["servis_dimulai"]        
    kendaraan.save()
    return HttpResponse('')

@jujojaz_login
def add_vehicle(request):
    user = User.objects.get(username=request.POST['username'])
    merk_nama = request.POST["merk"]
    merks = list(VehicleMerk.objects.filter(name=merk_nama))
    if (len(merks)):
        merk_object = merks[0]    
    else:
        merk_object = VehicleMerk(name=merk_nama)
        merk_object.save()

    type_nama = request.POST["tipe"]
    types = list(VehicleType.objects.filter(name=type_nama))
    if (len(types)):
        type_object = types[0]    
    else:
        type_object = VehicleType(name=type_nama)
        type_object.save()

    tipe = type_object
    merk = merk_object
    pajak_setiap_berapa_hari = int(request.POST["pajak_setiap_berapa_hari"])
    pajak_dimulai = request.POST["pajak_dimulai"]
    servis_setiap_berapa_hari = int(request.POST["servis_setiap_berapa_hari"])
    servis_dimulai = request.POST["servis_dimulai"]       
    foto_foto = str(user.id)+'_'+str(Vehicle.objects.filter(user=user).order_by('id')[:1][0])
    Vehicle(user=user, tipe=tipe, merk=merk, foto_foto=foto_foto, pajak_setiap_berapa_hari=pajak_setiap_berapa_hari, pajak_dimulai=pajak_dimulai, servis_setiap_berapa_hari=servis_setiap_berapa_hari, servis_dimulai=servis_dimulai).save()

    return HttpResponse('')

@jujojaz_login
def delete_vehicle(request):
    id_kendaraan = request.POST["id_kendaraan"]
    kendaraan = Vehicle.objects.get(id=id_kendaraan)
    kendaraan.delete()
    return HttpResponse('')

